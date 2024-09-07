package com.example.jetweatherforecast.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jetweatherforecast.WeatherApplication
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.di.DefaultAppContainer
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel /* @Inject constructor(private val repository: WeatherRepository)*/ //removed
class MainViewModel(private val repository: WeatherRepository): ViewModel() {
    /*val data: MutableState<DataOrException<Weather, Boolean, Exception>>
    = mutableStateOf(DataOrException(null, true, Exception(""))) //Why didn't we pass null to exception here

    init {
        //No DI (Left it for this section)(due to errors)

        loadWeather()
    }

    private fun loadWeather() {
        getWeather("seattle")
    }

    fun getWeather(city: String) {
        viewModelScope.launch {
            if(city.isEmpty()) return@launch
            data.value.loading = true
            data.value = repository.getWeather(cityQuery = city)
            if (data.value.data.toString().isNotEmpty()) data.value.loading = false
        Log.d("Inside ViewModel", "getWeather: ${data.value.data.toString().isNotEmpty()}")
        }
        Log.d("GET", "getWeather: ${data.value.data.toString()}")
    }*/

    suspend fun getWeatherData(city: String) : DataOrException<Weather,Boolean, Exception> {
        return repository.getWeather(cityQuery = city)
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                 val application = (this[APPLICATION_KEY] as WeatherApplication)
                val repository = application.container.weatherRepository
                MainViewModel(repository)

            }
        }
    }

}