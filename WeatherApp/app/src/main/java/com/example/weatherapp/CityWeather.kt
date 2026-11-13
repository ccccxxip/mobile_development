package com.example.weatherapp

data class CityWeather(
    val tempC: Double,
    val condition: String,
    val windDir: String,
    val windSpeed: Double
) {
    val tempF: Double
        get() = tempC * 1.8 + 32.0
}
