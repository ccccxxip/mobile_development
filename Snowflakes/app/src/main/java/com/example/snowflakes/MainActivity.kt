package com.example.snowflakes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var snowflakesView: SnowflakesView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        snowflakesView = SnowflakesView(this)
        setContentView(snowflakesView)
    }

    override fun onResume() {
        super.onResume()
        snowflakesView.startAnimation()
    }

    override fun onPause() {
        super.onPause()
        snowflakesView.stopAnimation()
    }
}
