package com.example.portraitlandscape

import org.junit.Assert.assertEquals
import org.junit.Test

class PortraitLandscapeLogicTest {

    @Test
    fun testCarCycling() {
        val totalCars = 3
        var current = 0

        current = (current + 1) % totalCars
        assertEquals(1, current)

        current = (current + 1) % totalCars
        assertEquals(2, current)

        current = (current + 1) % totalCars
        assertEquals(0, current)
    }

    @Test
    fun testValidCarIndexBounds() {
        fun validateIndex(idx: Int, total: Int): Int {
            return if (idx in 0 until total) idx else 0
        }

        assertEquals(0, validateIndex(-1, 3))
        assertEquals(2, validateIndex(2, 3))
        assertEquals(0, validateIndex(5, 3))
    }
}
