package com.tekinumut.eruyemekhane.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients
import com.tekinumut.eruyemekhane.data.repository.FoodListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FoodListRepository
) : ViewModel() {

    fun foodWithIngredientsByType(type: FoodListType) = repository.getFoodsByType(type)

    fun insertFoodWithIngredients(
        foodList: List<Food>,
        ingredientList: List<FoodIngredients>
    ) = viewModelScope.launch {
        repository.insertFoodAndIngredients(foodList, ingredientList)
    }


}