package com.example.jetweatherforecast

import android.app.Application
import com.example.jetweatherforecast.di.DefaultAppContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication: Application() {

    lateinit var container: DefaultAppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}