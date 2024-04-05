package com.bundev.gexplorer_mobile.data

import android.util.Log

sealed class ApiResource<T>(val data: T? = null, val error: Throwable? = null) {
    class Loading<T> : ApiResource<T>()

    class Error<T>(apiError: Throwable) : ApiResource<T>(error = apiError)
    class Success<T>(value: T) : ApiResource<T>(value, null)
    val kind: String
    get() = when(this) {
        is Error -> "error ${this.error}"
        is Loading -> "loading"
        is Success -> "success ${this.data}"
    }

    fun runIfLoading(block: (ApiResource<T>) -> Unit): ApiResource<T> {
        if (this is Loading) block(this)
        return this
    }

    fun runIfError(block: (ApiResource<T>) -> Unit): ApiResource<T> {
        if (this is Error) block(this)
        return this
    }

    fun runIfSuccess(block: (ApiResource<T>) -> Unit): ApiResource<T> {
        if (this is Success) block(this)
        return this
    }
    
    fun<R> mapSuccess(block: (T) -> R): ApiResource<R> = when (this) {
        is Success -> Success(block(this.data!!))
        is Error -> Error(this.error!!)
        is Loading -> Loading()
    }

    companion object {
        fun <T> fromResult(result: Result<T>) =
            result.fold(
                { Success(it) },
                { Error(it) }
            )
    }

}