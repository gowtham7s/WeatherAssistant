package com.example.weatherassistant.Model

data class FutureModel(
    val day: String,
    val picPath: String,
    val status: String,
    val highTemp: Int,
    val lowTemp: Int
)

data class CityWeather(
    val location: Location,
    val current: Current
) :java.io.Serializable

data class Current(
    val temperature: Int,
    val wind_speed: Int,
    val humidity: Int,
    val precip: Int,
    val weather_icons: ArrayList<String>,
) :java.io.Serializable

data class Location(
    val name: String,
    val region: String,
    val country: String,
) :java.io.Serializable