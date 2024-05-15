package dev.devlopment.webcallingapp.Repository


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

import com.google.firebase.auth.PhoneAuthOptions

class UserRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    var verificationId: String? = null
    var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun signUp(email: String, password: String, firstName: String, lastName: String, phoneNumber: String): Result<Boolean> =
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = User(firstName, lastName, email, phoneNumber)
            saveUserToFirestore(user)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }

    suspend fun saveUserToFirestore(user: User) {
        firestore.collection("users").document(user.email).set(user).await()
    }
    fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser
        return if (firebaseUser != null) {
            User(firebaseUser.displayName ?: "", "", firebaseUser.email ?: "", firebaseUser.phoneNumber ?: "")
        } else {
            null
        }
    }

    suspend fun login(email: String, password: String): Result<Boolean> =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }

    fun verifyOTP(otp: String, onVerificationComplete: () -> Unit, onVerificationFailed: (String) -> Unit) {
        try {
            val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onVerificationComplete()
                    } else {
                        onVerificationFailed("Verification failed")
                    }
                }
        } catch (e: Exception) {
            onVerificationFailed(e.message ?: "Verification failed")
        }
    }

    fun sendOTP(phoneNumber: String, callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
       var number = phoneNumber.trim()
        if (number.isNotEmpty()){
            if (number.length == 10){
                number = "+91$number"
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(number)       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)

            }else{

            }
        }else{


        }
    }

    fun resendOTP(phoneNumber: String, callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
        val options = resendToken?.let {
            PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setCallbacks(callbacks)
                .setForceResendingToken(it)// OnVerificationStateChangedCallbacks
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

}






