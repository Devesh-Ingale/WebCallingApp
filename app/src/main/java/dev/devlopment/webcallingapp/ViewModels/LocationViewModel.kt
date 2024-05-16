package dev.devlopment.webcallingapp.ViewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.devlopment.webcallingapp.Location.LocationData

class LocationViewModel:ViewModel() {
    private val _location= mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> =_location
    var address: String = ""


    fun updateLocation(newLocation:LocationData){
        _location.value=newLocation
    }
}