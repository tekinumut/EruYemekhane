package com.tekinumut.eruyemekhane.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private val _isFragmentCreatedBefore = MutableLiveData(false)
    val isFragmentCreatedBefore: LiveData<Boolean> = _isFragmentCreatedBefore

    fun setFragmentCreatedBefore(isCreatedBefore: Boolean) {
        _isFragmentCreatedBefore.value = isCreatedBefore
    }
}