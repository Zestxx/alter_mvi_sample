package com.example.alter_mvi_sample_chryev.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class SingleStateViewModel<T> : ViewModel() {

    val state: LiveData<T>
        get() = mutableState

    protected val stateValue
        get() = mutableState.value ?: getInitialState()

    private val mutableState = MutableLiveData<T>()

    init {
        mutableState.value = getInitialState()
    }

    abstract fun getInitialState(): T

    protected fun updateState(block: T.() -> T) {
        mutableState.value = block.invoke(stateValue)
    }

    protected fun updateState(state: T) {
        mutableState.value = state
    }
}
