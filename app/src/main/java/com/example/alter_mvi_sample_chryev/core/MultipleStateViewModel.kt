package com.example.alter_mvi_sample_chryev.core

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class MultipleStateViewModel<T>(
    private val initialState: LoadingState<T> = Loading()
) : ViewModel() {

    val state: LiveData<LoadingState<T>>
        get() = mutableState

    protected val stateValue
        get() = mutableState.value ?: initialState

    private val mutableState = MutableLiveData<LoadingState<T>>()

    init {
        mutableState.value = initialState
    }

    protected fun updateState(block: LoadingState<T>.() -> LoadingState<T>) {
        mutableState.value = block.invoke(stateValue)
    }

    protected fun updateState(state: LoadingState<T>) {
        mutableState.value = state
    }

    protected fun showContent(data: T) {
        updateState(ContentData(data))
    }

    protected fun showContent() {
        updateState(Content())
    }

    protected fun showStub(
        message: String,
        @DrawableRes image: Int? = null,
        @StringRes buttonText: Int? = null,
        onClicked: (() -> Unit)? = null
    ) {
        updateState(Stub(message, image, buttonText, onClicked))
    }

    protected fun showLoading() {
        updateState(Loading())
    }
}
