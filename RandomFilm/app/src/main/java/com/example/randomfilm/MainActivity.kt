package com.example.randomfilm

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val remainingMovies = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvMovie = findViewById<TextView>(R.id.tvMovie)

        if (savedInstanceState != null) {
            val savedList = savedInstanceState.getStringArrayList(KEY_REMAINING_MOVIES)
            if (savedList != null) {
                remainingMovies.clear()
                remainingMovies.addAll(savedList)
            } else {
                resetMoviesList()
            }
            val savedText = savedInstanceState.getString(KEY_CURRENT_TEXT)
            if (savedText != null) {
                tvMovie.text = savedText
            }
        } else {
            resetMoviesList()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(KEY_REMAINING_MOVIES, remainingMovies)
        val tvMovie = findViewById<TextView>(R.id.tvMovie)
        outState.putString(KEY_CURRENT_TEXT, tvMovie.text.toString())
    }

    @Suppress("UNUSED_PARAMETER")
    fun onGetFilmClick(view: View) {
        val tvMovie = findViewById<TextView>(R.id.tvMovie)
        if (remainingMovies.isNotEmpty()) {
            val index = Random.nextInt(remainingMovies.size)
            val selectedMovie = remainingMovies.removeAt(index)
            tvMovie.text = selectedMovie
        } else {
            tvMovie.text = getString(R.string.no_more_films)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun onResetClick(view: View) {
        val tvMovie = findViewById<TextView>(R.id.tvMovie)
        resetMoviesList()
        tvMovie.text = getString(R.string.initial_prompt)
    }

    private fun resetMoviesList() {
        remainingMovies.clear()
        val array = resources.getStringArray(R.array.movies)
        remainingMovies.addAll(array)
    }

    companion object {
        private const val KEY_REMAINING_MOVIES = "remaining_movies"
        private const val KEY_CURRENT_TEXT = "current_text"
    }
}
