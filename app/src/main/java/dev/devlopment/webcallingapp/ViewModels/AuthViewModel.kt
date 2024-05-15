package dev.devlopment.webcallingapp.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dev.devlopment.webcallingapp.MainAndUtils.Injection
import dev.devlopment.webcallingapp.MainAndUtils.SharedPreferencesManager
import dev.devlopment.webcallingapp.Repository.UserRepository
import dev.devlopment.webcallingapp.Repository.Result
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val userRepository: UserRepository = UserRepository(
        FirebaseAuth.getInstance(),
        Injection.instance()
    )

    init {
        SharedPreferencesManager.initialize()
        // Check if it's the first launch or if the user has logged out
        val isFirstLaunchOrLoggedOut = !SharedPreferencesManager.getBoolean("isLoggedIn", false)
        if (!isFirstLaunchOrLoggedOut) {
            attemptAutoLogin()
        }
    }

    private val _authResult = MutableLiveData<Result<Boolean>>()
    val authResult: LiveData<Result<Boolean>> get() = _authResult

    private val _loggedIn = MutableLiveData<Boolean>().apply { value = false }

    val loggedIn: LiveData<Boolean> get() = _loggedIn

    private val _otpVerificationResult = MutableLiveData<Result<Boolean>>()
    val otpVerificationResult: LiveData<Result<Boolean>> get() = _otpVerificationResult

    private fun attemptAutoLogin() {
        val storedEmail = SharedPreferencesManager.getString("email", "")
        val storedPassword = SharedPreferencesManager.getString("password", "")

        if (storedEmail.isNotEmpty() && storedPassword.isNotEmpty()) {
            login(storedEmail, storedPassword)
        } else {
            _loggedIn.value = false // Potential source of the NullPointerException
        }
    }

    fun signUp(email: String, password: String, firstName: String, lastName: String, phoneNumber: String) {
        viewModelScope.launch {
            _authResult.value = userRepository.signUp(email, password, firstName, lastName, phoneNumber)
            if (_authResult.value is Result.Success) {
                _loggedIn.value = true
                // User signed up successfully, now verify OTP
                _otpVerificationResult.value = Result.Success(false) // Reset OTP verification result
                sendOTP(phoneNumber) // Send OTP
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepository.login(email, password)
            if (result != null) {
                _authResult.value = result
                if (result is Result.Success) {
                    SharedPreferencesManager.saveString("email", email)
                    SharedPreferencesManager.saveString("password", password)
                    SharedPreferencesManager.saveBoolean("isLoggedIn", true)
                    _loggedIn.value = true
                }
            } else {
                // Handle the case when the result is null
            }
        }
    }

    private val _forgotPasswordResult: MutableLiveData<Result<Unit>> = MutableLiveData()
    val forgotPasswordResult: LiveData<Result<Unit>> = _forgotPasswordResult

    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            val result = userRepository.sendPasswordResetEmail(email)
            _forgotPasswordResult.postValue(result)
        }
    }
    suspend fun sendOTP(phoneNumber: String) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // Automatically handle verification if the code is received without user input
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // Handle verification failure
                _otpVerificationResult.value = Result.Error(e)
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                // Save the verification ID and resend token
                userRepository.verificationId = verificationId
                userRepository.resendToken = token
                _otpVerificationResult.value = Result.Success(true)
            }
        }
        userRepository.sendOTP(phoneNumber, callbacks)
    }


    fun resendOTP(phoneNumber: String) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                // Save the new verification ID and resend token
                userRepository.verificationId = verificationId
                userRepository.resendToken = token
                _otpVerificationResult.value = Result.Success(true)
            }

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                // Not implemented in this case
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                // Not implemented in this case
            }
        }
        userRepository.resendOTP(phoneNumber, callbacks)
    }

    fun verifyOTP(otp: String) {
        viewModelScope.launch {
            userRepository.verifyOTP(otp,
                onVerificationComplete = {
                    // Verification successful, now save the user
                    saveUser()

                },
                onVerificationFailed = { errorMessage ->
                    _otpVerificationResult.value = Result.Error(Exception(errorMessage))
                }
            )
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    _otpVerificationResult.value = Result.Success(true)
                } else {
                    // Handle sign in failure
                    _otpVerificationResult.value = Result.Error(task.exception ?: Exception("Sign in failed"))
                }
            }
    }


    fun saveUser() {
        // Fetch user details from UserRepository or SharedPreferences
        // and save them to Firestore
        val user = userRepository.getCurrentUser()
        if (user != null) {
            viewModelScope.launch {
                userRepository.saveUserToFirestore(user)
                _loggedIn.value = true // Set logged-in status to true
            }
        } else {
            _authResult.value = Result.Error(Exception("Failed to save user"))
        }
    }

    // Function to log out the user
    fun logout() {
        SharedPreferencesManager.clearAll() // Clear SharedPreferences
        SharedPreferencesManager.saveBoolean("isLoggedIn", false) // Update login status
        _loggedIn.value = false // Set logged-in status to false
    }

}



