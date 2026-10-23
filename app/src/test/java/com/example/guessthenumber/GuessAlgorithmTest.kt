package com.example.guessthenumber

import org.junit.Assert.assertEquals
import org.junit.Test

class GuessAlgorithmTest {

    private fun simulateGuess(target: Int, initialBegin: Int, initialEnd: Int): Int {
        var begin = initialBegin
        var end = initialEnd

        if (begin > end) {
            val temp = begin
            begin = end
            end = temp
        }

        while (begin < end) {
            if ((end - begin) == 1) {
                val isTargetEnd = (target == end)
                if (isTargetEnd) {
                    begin = end
                } else {
                    end = begin
                }
            } else {
                val middle = (begin + end) / 2
                if (target > middle) {
                    begin = middle + 1
                } else {
                    end = middle
                }
            }
        }
        return begin
    }

    @Test
    fun testAllNumbersFrom0To100() {
        for (target in 0..100) {
            val guessed = simulateGuess(target, 0, 100)
            assertEquals("Failed to guess target: $target", target, guessed)
        }
    }

    @Test
    fun testCustomRanges() {
        for (target in -50..50) {
            val guessed = simulateGuess(target, -50, 50)
            assertEquals("Failed to guess target: $target", target, guessed)
        }
    }

    @Test
    fun testSingleNumberRange() {
        assertEquals(42, simulateGuess(42, 42, 42))
    }

    @Test
    fun testTwoNumbersRange() {
        assertEquals(10, simulateGuess(10, 10, 11))
        assertEquals(11, simulateGuess(11, 10, 11))
    }
}
