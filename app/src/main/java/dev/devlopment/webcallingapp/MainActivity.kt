package dev.devlopment.webcallingapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dev.devlopment.chater.Navigations.NavigationGraph
import dev.devlopment.webcallingapp.ViewModels.AuthViewModel
import dev.devlopment.webcallingapp.ViewModels.LocationViewModel
import dev.devlopment.webcallingapp.ui.theme.WebCallingAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController= rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            val locationViewModel: LocationViewModel = viewModel()
            WebCallingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph(navController = navController, authViewModel = authViewModel, locationViewModel = locationViewModel)
                }
            }
        }
    }
}

