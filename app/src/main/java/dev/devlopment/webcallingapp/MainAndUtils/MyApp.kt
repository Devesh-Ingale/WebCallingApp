package dev.devlopment.webcallingapp.MainAndUtils

import android.app.Application

class MyApp : Application() {

    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Any application-wide initialization can be done here
    }

}