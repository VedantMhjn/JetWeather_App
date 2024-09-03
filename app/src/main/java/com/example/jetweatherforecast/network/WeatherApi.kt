package com.example.jetweatherforecast.network

import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherObject
import com.example.jetweatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("Units") units: String = "imperial",
        @Query("appid") appid: String = Constants.API_KEY
    ): Weather
//This Weather data class will be used here instead of WeatherObject(Its my conscience)
}