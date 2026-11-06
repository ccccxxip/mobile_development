package com.example.mvvmdemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val viewModel: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvCounter = findViewById<TextView>(R.id.tvCounter)
        val btnIncrement = findViewById<Button>(R.id.btnIncrement)
        val btnDecrement = findViewById<Button>(R.id.btnDecrement)
        val btnReset = findViewById<Button>(R.id.btnReset)

        // Observe ViewModel counter state
        viewModel.counter.observe(this) { count ->
            tvCounter.text = count.toString()
        }

        btnIncrement.setOnClickListener {
            viewModel.increment()
        }

        btnDecrement.setOnClickListener {
            viewModel.decrement()
        }

        btnReset.setOnClickListener {
            viewModel.reset()
        }
    }
}
