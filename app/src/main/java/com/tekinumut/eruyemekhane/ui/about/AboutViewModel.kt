package com.tekinumut.eruyemekhane.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor() : ViewModel() {

    private val _isExpanded = MutableLiveData(false)
    val isExpanded: LiveData<Boolean> = _isExpanded

    fun setIsExpanded(isExpanded: Boolean) {
        _isExpanded.value = isExpanded
    }

    var isLottiePlayed = false
}