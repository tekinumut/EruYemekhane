package com.tekinumut.eruyemekhane.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(){
    var isFragmentCreatedBefore = false
}