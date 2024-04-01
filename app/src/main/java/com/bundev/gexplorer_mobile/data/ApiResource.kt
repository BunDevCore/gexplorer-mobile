package com.bundev.gexplorer_mobile.data

sealed class ApiResource<T>(val data: T? = null, val error: Boolean = false) {
    class Loading<T> : ApiResource<T>()
    class Error<T> : ApiResource<T>(error = true)
    data class Success<T>(val value: T) : ApiResource<T>(value, false)
}