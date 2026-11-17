package com.example.currentweatherdatabinding

import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherDataTest {

    @Test
    fun testDataBindingFormattedProperties() {
        val weather = WeatherData(
            cityName = "Иркутск",
            tempC = -5.8,
            condition = "Снег",
            windDir = "СВ",
            windSpeed = 3.5,
            humidity = 82,
            pressure = 1018
        )

        assertEquals("Иркутск", weather.cityName)
        assertEquals("-6°C", weather.tempFormatted)
        assertEquals("Ветер: СВ, 3.5 м/с", weather.windFormatted)
        assertEquals("Влажность: 82%", weather.humidityFormatted)
        assertEquals("Давление: 1018 гПа", weather.pressureFormatted)
    }
}
