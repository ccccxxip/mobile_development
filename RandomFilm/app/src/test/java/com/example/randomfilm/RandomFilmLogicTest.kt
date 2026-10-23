package com.example.randomfilm

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class RandomFilmLogicTest {

    @Test
    fun testMoviesDoNotRepeatAndDeplete() {
        val initialMovies = listOf("Film 1", "Film 2", "Film 3", "Film 4", "Film 5")
        val remaining = ArrayList(initialMovies)
        val shownMovies = mutableListOf<String>()

        while (remaining.isNotEmpty()) {
            val index = Random.nextInt(remaining.size)
            val selected = remaining.removeAt(index)
            assertFalse("Movie $selected was repeated!", shownMovies.contains(selected))
            shownMovies.add(selected)
        }

        assertTrue("Remaining list should be empty", remaining.isEmpty())
        assertEquals("All 5 movies should have been shown once", 5, shownMovies.size)
    }

    @Test
    fun testResetRestoresFullList() {
        val initialMovies = listOf("Film A", "Film B", "Film C")
        val remaining = ArrayList(initialMovies)

        remaining.removeAt(0)
        remaining.removeAt(0)
        assertEquals(1, remaining.size)

        remaining.clear()
        remaining.addAll(initialMovies)
        assertEquals(3, remaining.size)
    }
}
