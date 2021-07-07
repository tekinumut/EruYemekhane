package com.tekinumut.eruyemekhane.utils

sealed class Resource<out T>(val status: Status) {
    object Loading : Resource<Nothing>(Status.LOADING)
    class Success<T>(val data: T) : Resource<T>(Status.SUCCESS)
    class Error(val errorMessage: String) : Resource<Nothing>(Status.ERROR)

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR
    }
}