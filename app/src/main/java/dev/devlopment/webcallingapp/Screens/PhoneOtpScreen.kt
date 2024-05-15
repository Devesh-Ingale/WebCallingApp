package dev.devlopment.webcallingapp.Screens


import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dev.devlopment.webcallingapp.R
import dev.devlopment.webcallingapp.Repository.Result
import dev.devlopment.webcallingapp.ViewModels.AuthViewModel
import dev.devlopment.webcallingapp.ui.theme.Black
import dev.devlopment.webcallingapp.ui.theme.BlueGray
import dev.devlopment.webcallingapp.ui.theme.Roboto
import dev.devlopment.webcallingapp.ui.theme.focusedTextFieldText
import dev.devlopment.webcallingapp.ui.theme.textFieldContainer
import dev.devlopment.webcallingapp.ui.theme.unfocusedTextFieldText


@Composable
fun PhoneOtpScreen(
    authViewModel: AuthViewModel,
    OnNavigateToSignUp: () -> Unit,
    onOtpSuccess: () -> Unit
) {
    var PhoneNumber by remember { mutableStateOf("") }
    var Otp by remember {
        mutableStateOf("")
    }
    val result by authViewModel.authResult.observeAsState()




    LaunchedEffect(result) {
        result?.let { authResult ->
            when (authResult) {
                is Result.Success -> {

                }

                is Result.Error -> {
                    // Handle error if needed
                }
            }
        }
    }



    Surface {
        Column(modifier = Modifier.fillMaxSize()) {

            val uiColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black
            val background: Painter = if (isSystemInDarkTheme()) {
                painterResource(id = R.drawable.shapedark)
            } else {
                painterResource(id = R.drawable.shapelight)
            }

            Box(
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.46f),
                    painter = background,
                    contentDescription = "Shape",
                    contentScale = ContentScale.FillBounds
                )
                Row(
                    modifier = Modifier.padding(top = 80.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {

                    Icon(
                        modifier = Modifier.size(42.dp),
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = stringResource(id = R.string.app_name),
                        tint = uiColor
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.headlineMedium,
                            color = uiColor
                        )

                        Text(
                            text = stringResource(id = R.string.headline),
                            style = MaterialTheme.typography.titleMedium,
                            color = uiColor
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .align(alignment = Alignment.BottomCenter),
                    text = "Phone Verification",
                    style = MaterialTheme.typography.headlineLarge,
                    color = uiColor
                )

            }

            Spacer(modifier = Modifier.height(36.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                Spacer(modifier = Modifier.height(15.dp))

                TextField(modifier = Modifier.fillMaxWidth(),
                    value = Otp,
                    onValueChange = { Otp = it },
                    label = {
                        Text(
                            text = "OTP",
                            style = MaterialTheme.typography.labelMedium,
                            color = uiColor
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText,
                        unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    trailingIcon = {
                        TextButton(onClick = { authViewModel.resendOTP(phoneNumber = PhoneNumber) }) {
                            Text(
                                text = "Resend",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                                color = uiColor
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Existing Code...

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    onClick = { authViewModel.verifyOTP(Otp) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) BlueGray else Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(size = 4.dp)
                ) {
                    Text(
                        text = "Log in",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))  // Add Spacer for spacing

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp)
                ) {
                    // Existing TextField components...


                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxHeight(fraction = 0.8f)
                            .fillMaxWidth()
                            .clickable { OnNavigateToSignUp() },
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = Color(0xFF94A3B8),
                                        fontSize = 14.sp,
                                        fontFamily = Roboto,
                                        fontWeight = FontWeight.Normal
                                    )
                                ) {
                                    append("Don't have Account?")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = uiColor,
                                        fontSize = 14.sp,
                                        fontFamily = Roboto,
                                        fontWeight = FontWeight.Medium
                                    )
                                ) {
                                    append(" ")
                                    append("Sign up")
                                }
                            }
                        )
                    }
                }


            }
        }
    }
}



