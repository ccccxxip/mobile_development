package com.example.portraitlandscape

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val carImages = intArrayOf(
        R.drawable.car1,
        R.drawable.car2,
        R.drawable.car3,
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        currentIndex = prefs.getInt(KEY_SELECTED_CAR, 0)
        if ((currentIndex < 0) || (currentIndex >= carImages.size)) {
            currentIndex = 0
        }

        val imageViewCar = findViewById<ImageView>(R.id.imageViewCar)
        val spinnerCars = findViewById<Spinner>(R.id.spinnerCars)
        val buttonNextCar = findViewById<Button>(R.id.buttonNextCar)

        // Set up Spinner adapter
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.cars,
            android.R.layout.simple_spinner_item,
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCars.adapter = adapter

        // Set initial state
        imageViewCar.setImageResource(carImages[currentIndex])
        spinnerCars.setSelection(currentIndex)

        // Spinner item selection listener
        spinnerCars.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != currentIndex) {
                    currentIndex = position
                    imageViewCar.setImageResource(carImages[currentIndex])
                    saveCarIndex(currentIndex)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Button click listener to cycle through cars
        buttonNextCar.setOnClickListener {
            currentIndex = (currentIndex + 1) % carImages.size
            imageViewCar.setImageResource(carImages[currentIndex])
            spinnerCars.setSelection(currentIndex)
            saveCarIndex(currentIndex)
        }
    }

    private fun saveCarIndex(index: Int) {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        prefs.edit { putInt(KEY_SELECTED_CAR, index) }
    }

    companion object {
        private const val PREFS_NAME = "car_prefs"
        private const val KEY_SELECTED_CAR = "selected_car_index"
    }
}
