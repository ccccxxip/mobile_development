package com.example.mvvmdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {

    private val model = CounterModel(0)

    private val _counter = MutableLiveData<Int>(model.count)
    val counter: LiveData<Int> get() = _counter

    fun increment() {
        _counter.value = model.increment()
    }

    fun decrement() {
        _counter.value = model.decrement()
    }

    fun reset() {
        _counter.value = model.reset()
    }
}
