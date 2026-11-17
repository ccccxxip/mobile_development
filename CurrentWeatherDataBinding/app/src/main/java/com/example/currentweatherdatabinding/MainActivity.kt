package com.example.currentweatherdatabinding

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.currentweatherdatabinding.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val apiKey = "e46f6630f9a7aa4e5c54848d73b22cfd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация Data Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Начальное значение данных
        binding.weather = WeatherData(cityName = "Загрузка...", condition = "Пожалуйста, подождите")

        // Настройка Spinner
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.cities,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCity.adapter = adapter

        binding.spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val cities = resources.getStringArray(R.array.cities)
                if (position in cities.indices) {
                    val cityName = cities[position]
                    fetchWeatherDataWithCoroutines(cityName)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun fetchWeatherDataWithCoroutines(cityName: String) {
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
                val humidity = main.getInt("humidity")
                val pressure = main.getInt("pressure")

                val weatherArray = json.getJSONArray("weather")
                val condition = if (weatherArray.length() > 0) {
                    weatherArray.getJSONObject(0).getString("description").replaceFirstChar { it.uppercase() }
                } else "Ясно"

                val wind = json.getJSONObject("wind")
                val windSpeed = wind.getDouble("speed")
                val deg = wind.optInt("deg", 0)
                val windDir = getWindDirectionFromDegrees(deg)

                val weatherObj = WeatherData(
                    cityName = cityName,
                    tempC = tempC,
                    condition = condition,
                    windDir = windDir,
                    windSpeed = windSpeed,
                    humidity = humidity,
                    pressure = pressure
                )

                // Привязка новых данных через Data Binding на главном потоке
                withContext(Dispatchers.Main) {
                    binding.weather = weatherObj
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    binding.weather = WeatherData(
                        cityName = cityName,
                        tempC = 0.0,
                        condition = "Ошибка загрузки или оффлайн",
                        windDir = "З",
                        windSpeed = 2.0,
                        humidity = 70,
                        pressure = 1012
                    )
                }
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
}
