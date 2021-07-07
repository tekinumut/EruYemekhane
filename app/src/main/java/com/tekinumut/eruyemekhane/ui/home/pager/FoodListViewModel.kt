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

    var isCreatedBefore = false

    private val _foodListType = MutableLiveData<Pair<FoodListType, Boolean>>()

    val foodList = _foodListType.switchMap { pair ->
        repository.getFoodList(pair.first, pair.second)
    }

    fun fetchFoodList(foodListType: FoodListType, shouldFetch: Boolean) {
        _foodListType.value = Pair(foodListType, shouldFetch)
    }
}