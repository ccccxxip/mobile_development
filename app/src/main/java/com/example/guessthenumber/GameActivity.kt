package com.example.guessthenumber

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    var begin: Int = 0
    var end: Int = 100
    private var middle: Int = 0
    private var isFinalQuestion: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        begin = intent.getIntExtra("begin", 0)
        end = intent.getIntExtra("end", 100)

        Log.d("mytag", "begin = $begin")
        Log.d("mytag", "end = $end")

        if (begin > end) {
            val temp = begin
            begin = end
            end = temp
        }

        askQuestion()
    }

    private fun askQuestion() {
        val tvQuestion = findViewById<TextView>(R.id.question)
        val btnYes = findViewById<Button>(R.id.yes)
        val btnNo = findViewById<Button>(R.id.no)

        if (begin == end) {
            tvQuestion.text = getString(R.string.result_format, begin)
            btnYes.visibility = View.GONE
            btnNo.visibility = View.GONE
            return
        }

        if ((end - begin) == 1) {
            isFinalQuestion = true
            tvQuestion.text = getString(R.string.question_is_number, end)
        } else {
            isFinalQuestion = false
            middle = (begin + end) / 2
            tvQuestion.text = getString(R.string.question_greater_than, middle)
        }
    }

    fun onYesNoClick(view: View) {
        val tvQuestion = findViewById<TextView>(R.id.question)
        val btnYes = findViewById<Button>(R.id.yes)
        val btnNo = findViewById<Button>(R.id.no)

        if (begin == end) return

        when (view.id) {
            R.id.yes -> {
                Log.d("mytag", "yes")
                if (isFinalQuestion) {
                    begin = end
                    tvQuestion.text = getString(R.string.result_success, end)
                    btnYes.visibility = View.GONE
                    btnNo.visibility = View.GONE
                } else {
                    begin = middle + 1
                    askQuestion()
                }
            }
            R.id.no -> {
                Log.d("mytag", "no")
                if (isFinalQuestion) {
                    end = begin
                    tvQuestion.text = getString(R.string.result_success, begin)
                    btnYes.visibility = View.GONE
                    btnNo.visibility = View.GONE
                } else {
                    end = middle
                    askQuestion()
                }
            }
        }
    }
}
