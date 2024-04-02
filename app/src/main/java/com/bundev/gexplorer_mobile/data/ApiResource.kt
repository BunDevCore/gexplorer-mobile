package com.bundev.gexplorer_mobile.data

sealed class ApiResource<T>(val data: T? = null, val error: Throwable? = null) {
    class Loading<T> : ApiResource<T>()
    class Error<T>(apiError: Throwable) : ApiResource<T>(error = apiError)
    class Success<T>(value: T) : ApiResource<T>(value, null)

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

    companion object {
        fun <T> fromResult(result: Result<T>) =
            result.fold(
                { Success(it) },
                { Error(it) }
            )
    }

}