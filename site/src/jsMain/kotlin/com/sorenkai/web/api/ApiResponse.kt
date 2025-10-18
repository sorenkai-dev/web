package com.sorenkai.web.api

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class HttpError(val code: Int, val message: String) : ApiResponse<Nothing>()
    data class NetworkError(val message: String) : ApiResponse<Nothing>()
    data class UnknownError(val message: String) : ApiResponse<Nothing>()

    inline fun <R> map(transform: (T) -> R): ApiResponse<R> = when (this) {
        is Success -> Success(transform(data))
        is HttpError -> HttpError(code, message)
        is NetworkError -> NetworkError(message)
        is UnknownError -> UnknownError(message)
    }
}
