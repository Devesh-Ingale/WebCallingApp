package dev.devlopment.webcallingapp.Screens

import android.Manifest

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.devlopment.chater.Navigations.Screen
import dev.devlopment.webcallingapp.Location.LocationUtil
import dev.devlopment.webcallingapp.MainActivity
import dev.devlopment.webcallingapp.ViewModels.LocationViewModel

@Composable
fun MyApp(viewModel: LocationViewModel,navController: NavController){
    val context= LocalContext.current
    val locationUtils=LocationUtil(context)
    LocationDisplay(locationUtil = locationUtils,viewModel, context = context,navController=navController)
}

@Composable
fun LocationDisplay(
    locationUtil: LocationUtil,
    viewModel: LocationViewModel,
    context: Context,
    navController: NavController
){
    val location=viewModel.location.value
    val address=location?.let {
        locationUtil.reverseGeocodeLocation(location = location)
    }
    // When obtaining the location and address


    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract =ActivityResultContracts.RequestMultiplePermissions() ,
        onResult ={
                permissions->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION]==true &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION]==true){
                // i have permission access
                locationUtil.requestLocationUpdates(viewModel = viewModel)
            }else{
                //Ask for permission
                val rationaleRequired=ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )||ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if (rationaleRequired){
                    Toast.makeText(context,"Location Permission is required for this feature to work",
                        Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"Location Permission is required Please enable it in Settings",
                        Toast.LENGTH_LONG).show()
                }
            }
            if (location!=null){
                if (address != null) {
                    viewModel.address = address
                }
            }
        } )


    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        if (location!=null){
            Text(text = "Address: ${location.latitude} ${location.longitude}\n $address")
            if (address != null) {
                viewModel.address = address
            }
            navController.navigateUp()
        }else {
            Text(text = "Location not available")
        }

        Button(onClick = { if (locationUtil.hasLocationPermission(context)){
            //permission granted
            locationUtil.requestLocationUpdates(viewModel)
        }else{
            //request permission
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
        }
        ) {
            Text(text = "Get Location")
        }
    }
}