package com.example.currentweatherdatabinding

import java.util.Locale

data class WeatherData(
    val cityName: String = "",
    val tempC: Double = 0.0,
    val condition: String = "",
    val windDir: String = "",
    val windSpeed: Double = 0.0,
    val humidity: Int = 0,
    val pressure: Int = 0
) {
    val tempFormatted: String
        get() = String.format(Locale.US, "%.0f°C", tempC)

    val windFormatted: String
        get() = String.format(Locale.US, "Ветер: %s, %.1f м/с", windDir, windSpeed)

    val humidityFormatted: String
        get() = "Влажность: $humidity%"

    val pressureFormatted: String
        get() = "Давление: $pressure гПа"
}
