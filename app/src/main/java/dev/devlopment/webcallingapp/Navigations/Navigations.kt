package dev.devlopment.chater.Navigations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.devlopment.webcallingapp.Screens.LoginScreen
import dev.devlopment.webcallingapp.Screens.PhoneOtpScreen
import dev.devlopment.webcallingapp.Screens.SignUpScreen
import dev.devlopment.webcallingapp.ViewModels.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val loggedInUser = authViewModel.loggedIn.value

    // Navigate based on the authentication result
    LaunchedEffect(loggedInUser) {
        loggedInUser?.let {
            if (it) {
                //todo
            } else {
                navController.navigate(Screen.LoginScreen.route)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.SignupScreen.route) {
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.LoginScreen.route) },
                onNavigateToOTP = {navController.navigate(Screen.PhoneOtpScreen.route)}
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                authViewModel = authViewModel,
                OnNavigateToSignUp = { navController.navigate(Screen.SignupScreen.route) },
                onLoginSuccess = {
                    //todo
                }
            )
        }
        composable(Screen.PhoneOtpScreen.route) {
            PhoneOtpScreen(authViewModel = authViewModel, OnNavigateToSignUp = { /*TODO*/ }) {
                
            }

        }
    }
}
