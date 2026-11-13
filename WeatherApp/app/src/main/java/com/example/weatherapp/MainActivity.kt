package com.example.weatherapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val weatherData = mutableMapOf<String, CityWeather>()
    private var selectedCity: String = ""

    private val apiKey = "e46f6630f9a7aa4e5c54848d73b22cfd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadWeatherDataFromJson()

        val spinnerCity = findViewById<Spinner>(R.id.spinnerCity)
        val rgTempUnit = findViewById<RadioGroup>(R.id.rgTempUnit)
        val cbShowWind = findViewById<CheckBox>(R.id.cbShowWind)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.cities,
            android.R.layout.simple_spinner_item,
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCity.adapter = adapter

        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val cities = resources.getStringArray(R.array.cities)
                if (position in cities.indices) {
                    selectedCity = cities[position]
                    fetchLiveWeatherForCity(selectedCity)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        rgTempUnit.setOnCheckedChangeListener { _, _ ->
            updateWeatherDisplay()
        }

        cbShowWind.setOnCheckedChangeListener { _, _ ->
            updateWeatherDisplay()
        }
    }

    private fun loadWeatherDataFromJson() {
        try {
            val jsonString = assets.open("weather.json").bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val city = keys.next()
                val cityObj = jsonObject.getJSONObject(city)
                val tempC = cityObj.getDouble("tempC")
                val condition = cityObj.getString("condition")
                val windDir = cityObj.getString("windDir")
                val windSpeed = cityObj.getDouble("windSpeed")
                weatherData[city] = CityWeather(tempC, condition, windDir, windSpeed)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun fetchLiveWeatherForCity(cityName: String) {
        updateWeatherDisplay()

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val cityQuery = when (cityName) {
                    "Москва" -> "Moscow,RU"
                    "Санкт-Петербург" -> "Saint Petersburg,RU"
                    "Новосибирск" -> "Novosibirsk,RU"
                    "Екатеринбург" -> "Yekaterinburg,RU"
                    "Казань" -> "Kazan,RU"
                    "Иркутск" -> "Irkutsk,RU"
                    else -> cityName
                }
                val urlString = "https://api.openweathermap.org/data/2.5/weather?q=$cityQuery&appid=$apiKey&units=metric&lang=ru"
                val response = URL(urlString).readText()
                val json = JSONObject(response)

                val main = json.getJSONObject("main")
                val tempC = main.getDouble("temp")

                val weatherArray = json.getJSONArray("weather")
                val condition = if (weatherArray.length() > 0) {
                    weatherArray.getJSONObject(0).getString("description").replaceFirstChar { it.uppercase() }
                } else "Облачно"

                val wind = json.getJSONObject("wind")
                val windSpeed = wind.getDouble("speed")
                val deg = wind.optInt("deg", 0)
                val windDir = getWindDirectionFromDegrees(deg)

                val liveWeather = CityWeather(tempC, condition, windDir, windSpeed)

                withContext(Dispatchers.Main) {
                    weatherData[cityName] = liveWeather
                    if (selectedCity == cityName) {
                        updateWeatherDisplay()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getWindDirectionFromDegrees(deg: Int): String {
        return when (deg) {
            in 23..67 -> "Северо-Восточный (СВ)"
            in 68..112 -> "Восточный (В)"
            in 113..157 -> "Юго-Восточный (ЮВ)"
            in 158..202 -> "Южный (Ю)"
            in 203..247 -> "Юго-Западный (ЮЗ)"
            in 248..292 -> "Западный (З)"
            in 293..337 -> "Северо-Западный (СЗ)"
            else -> "Северный (С)"
        }
    }

    private fun updateWeatherDisplay() {
        val tvCityName = findViewById<TextView>(R.id.tvCityName)
        val tvTemperature = findViewById<TextView>(R.id.tvTemperature)
        val tvCondition = findViewById<TextView>(R.id.tvCondition)
        val tvWind = findViewById<TextView>(R.id.tvWind)
        val rbCelsius = findViewById<RadioButton>(R.id.rbCelsius)
        val cbShowWind = findViewById<CheckBox>(R.id.cbShowWind)

        val weather = weatherData[selectedCity] ?: return

        tvCityName.text = selectedCity
        tvCondition.text = weather.condition

        if (rbCelsius.isChecked) {
            tvTemperature.text = String.format(Locale.getDefault(), "%.0f°C", weather.tempC)
        } else {
            tvTemperature.text = String.format(Locale.getDefault(), "%.1f°F", weather.tempF)
        }

        if (cbShowWind.isChecked) {
            tvWind.visibility = View.VISIBLE
            tvWind.text = getString(R.string.wind_format, weather.windDir, weather.windSpeed)
        } else {
            tvWind.visibility = View.GONE
        }
    }
}
