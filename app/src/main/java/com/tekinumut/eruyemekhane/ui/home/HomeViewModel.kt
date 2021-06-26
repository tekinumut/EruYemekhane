package com.tekinumut.eruyemekhane.ui.home

import androidx.lifecycle.ViewModel
import com.tekinumut.eruyemekhane.data.repository.FoodListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FoodListRepository
) : ViewModel()