package com.example.guessthenumber

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Suppress("UNUSED_PARAMETER")
    fun onGuessClick(view: View) {
        val etBegin = findViewById<EditText>(R.id.editTextBegin)
        val etEnd = findViewById<EditText>(R.id.editTextEnd)

        val beginStr = etBegin?.text?.toString() ?: ""
        val endStr = etEnd?.text?.toString() ?: ""

        val begin = beginStr.toIntOrNull() ?: 0
        val end = endStr.toIntOrNull() ?: 100

        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("begin", begin)
        intent.putExtra("end", end)
        startActivity(intent)
    }
}
