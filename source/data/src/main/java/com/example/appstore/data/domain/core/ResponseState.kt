package com.example.appstore.data.domain.core

sealed class ResponseState<out T> {
    class Success<T>(val data: T) : ResponseState<T>()
    class Error<T>(val error: ErrorState) : ResponseState<T>()
    object Loading : ResponseState<Nothing>()
    object Retry : ResponseState<Nothing>()
}