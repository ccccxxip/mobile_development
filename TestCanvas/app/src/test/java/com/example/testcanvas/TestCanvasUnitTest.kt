package com.example.testcanvas

import org.junit.Assert.assertEquals
import org.junit.Test

class TestCanvasUnitTest {

    @Test
    fun testViewBoundsCalculation() {
        val left = 0
        val top = 0
        val right = 1080
        val bottom = 1920

        val width = right - left
        val height = bottom - top

        assertEquals(1080, width)
        assertEquals(1920, height)
    }
}
