package com.example.mvvmdemo

class CounterModel(initialValue: Int = 0) {
    var count: Int = initialValue
        private set

    fun increment(): Int {
        count++
        return count
    }

    fun decrement(): Int {
        count--
        return count
    }

    fun reset(): Int {
        count = 0
        return count
    }
}
