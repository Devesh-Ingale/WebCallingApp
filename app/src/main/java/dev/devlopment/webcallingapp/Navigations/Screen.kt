package dev.devlopment.chater.Navigations

sealed class Screen(val route:String){
    object LoginScreen: Screen("loginscreen")
    object SignupScreen: Screen("signupscreen")
    object PhoneOtpScreen: Screen("phoneotpscreen")

}