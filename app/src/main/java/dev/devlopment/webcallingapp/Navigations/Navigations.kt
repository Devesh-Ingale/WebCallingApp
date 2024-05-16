package dev.devlopment.chater.Navigations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.devlopment.webcallingapp.Screens.LoginScreen
import dev.devlopment.webcallingapp.Screens.MyApp
import dev.devlopment.webcallingapp.Screens.PhoneOtpScreen
import dev.devlopment.webcallingapp.Screens.Profile
import dev.devlopment.webcallingapp.Screens.SignUpScreen
import dev.devlopment.webcallingapp.ViewModels.AuthViewModel
import dev.devlopment.webcallingapp.ViewModels.LocationViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    locationViewModel: LocationViewModel
) {
    val loggedInUser = authViewModel.loggedIn.value

    // Navigate based on the authentication result
    LaunchedEffect(loggedInUser) {
        loggedInUser?.let {
            if (it) {
                navController.navigate(Screen.ProfileScreen.route)
            } else {
                navController.navigate(Screen.SignupScreen.route)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.SignupScreen.route
    ) {
        composable(Screen.SignupScreen.route) {
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.LoginScreen.route) },
                onNavigateToOTP = {navController.navigate(Screen.PhoneOtpScreen.route)},
                onNavigateTolocation = {navController.navigate(Screen.LocationDisplayScreen.route)},
                locationViewModel = locationViewModel
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                authViewModel = authViewModel,
                OnNavigateToSignUp = { navController.navigate(Screen.SignupScreen.route) },
                onLoginSuccess = {
                    navController.navigate(Screen.ProfileScreen.route)
                }
            )
        }
        composable(Screen.PhoneOtpScreen.route) {
            PhoneOtpScreen(authViewModel = authViewModel,
                onOtpSuccess = {navController.navigate(Screen.ProfileScreen.route)}
            )


        }

        composable(Screen.ProfileScreen.route) {
            Profile(viewModel = authViewModel,onNavigateToLogin = {navController.navigate(Screen.LoginScreen.route)})
        }

        composable(Screen.LocationDisplayScreen.route) {
            MyApp(locationViewModel, navController = navController)
        }

    }
}
