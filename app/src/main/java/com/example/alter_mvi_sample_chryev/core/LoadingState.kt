package com.example.alter_mvi_sample_chryev.core

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class LoadingState<T> {

    fun asContent(): T {
        return (this as ContentData<T>).content
    }

    fun asError(): Throwable {
        return (this as Error).value
    }

    fun asContentOrNull(): T? {
        return (this as? ContentData<T>)?.content
    }

    fun asErrorOrNull(): Throwable? {
        return (this as? Error)?.value
    }
}

class Loading<T> : LoadingState<T>()

/*Content*/
class Content<T> : LoadingState<T>()
data class ContentData<T>(val content: T) : LoadingState<T>()
/**/

/*Stub*/
data class Stub<T>(
    val message: String,
    @DrawableRes val image: Int? = null,
    @StringRes val buttonText: Int? = null,
    val onClicked: (() -> Unit)? = null
) : LoadingState<T>()
/**/

/*Error*/
data class Error<T>(val value: Throwable = Throwable("Unknown error")) : LoadingState<T>()
/**/
