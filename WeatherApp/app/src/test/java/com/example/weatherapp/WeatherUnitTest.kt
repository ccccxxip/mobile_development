package com.example.weatherapp

import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherUnitTest {

    @Test
    fun testTemperatureConversion() {
        val weather = CityWeather(
            tempC = 20.0,
            condition = "Sunny",
            windDir = "S",
            windSpeed = 3.0
        )

        assertEquals(20.0, weather.tempC, 0.001)
        assertEquals(68.0, weather.tempF, 0.001)
    }

    @Test
    fun testZeroCelsiusFahrenheitConversion() {
        val weather = CityWeather(
            tempC = 0.0,
            condition = "Cold",
            windDir = "N",
            windSpeed = 10.0
        )

        assertEquals(32.0, weather.tempF, 0.001)
    }
}
