package com.example.mvvmdemo

import org.junit.Assert.assertEquals
import org.junit.Test

class CounterMVVMTest {

    @Test
    fun testModelOperations() {
        val model = CounterModel(0)
        assertEquals(0, model.count)

        assertEquals(1, model.increment())
        assertEquals(2, model.increment())

        assertEquals(1, model.decrement())

        assertEquals(0, model.reset())
        assertEquals(0, model.count)
    }
}
