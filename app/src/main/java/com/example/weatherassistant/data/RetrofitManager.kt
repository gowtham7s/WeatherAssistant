package com.example.weatherassistant.data

// Retrofit interface

import com.example.weatherassistant.Model.CityModel
import com.example.weatherassistant.Model.CityWeather
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/current?access_key=0e5e622167a2eab01d8c10557f7273c9")
    fun getWeather(@Query("query") city: String): Call<CityWeather>
}

interface QuotesApi {
    @GET("")
    suspend fun getQuotes() : Response<CityWeather>
}

