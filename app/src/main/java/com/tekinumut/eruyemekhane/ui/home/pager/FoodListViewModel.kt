package com.tekinumut.eruyemekhane.ui.home.pager

import androidx.lifecycle.ViewModel
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.repository.FoodListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val repository: FoodListRepository
) : ViewModel() {
    fun getFoodList(type: FoodListType) = repository.getFoodList(type)
}