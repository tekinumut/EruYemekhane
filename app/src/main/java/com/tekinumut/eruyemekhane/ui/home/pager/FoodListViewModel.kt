package com.tekinumut.eruyemekhane.ui.home.pager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.tekinumut.eruyemekhane.base.BaseViewModel
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.repository.FoodListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val repository: FoodListRepository
) : BaseViewModel() {

    private val _foodListType = MutableLiveData<FoodListType>()

    val foodList = _foodListType.switchMap { type ->
        repository.getFoodList(type)
    }

    fun fetchFoodList(foodListType: FoodListType) {
        _foodListType.value = foodListType
    }
}