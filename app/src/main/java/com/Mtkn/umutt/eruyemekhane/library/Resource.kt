package com.Mtkn.umutt.eruyemekhane.library

sealed class Resource<out T : Any> {
    object InProgress : Resource<Nothing>()
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}