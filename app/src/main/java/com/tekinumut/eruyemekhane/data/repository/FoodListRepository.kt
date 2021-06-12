package com.tekinumut.eruyemekhane.data.repository

import androidx.lifecycle.LiveData
import com.tekinumut.eruyemekhane.base.BaseDataSource
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.local.FoodDao
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients
import com.tekinumut.eruyemekhane.data.model.FoodWithIngredients
import com.tekinumut.eruyemekhane.data.remote.MainApiService
import com.tekinumut.eruyemekhane.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListRepository @Inject constructor(
    private val mainApi: MainApiService,
    private val foodDao: FoodDao
) : BaseDataSource() {

    fun getStudentFoodList(): LiveData<Resource<String>> = observeApi {
        mainApi.getStudentFoodList()
    }

    fun getPersonalFoodList(): LiveData<Resource<String>> = observeApi {
        mainApi.getStudentFoodList()
    }

    fun getFoodsByType(type: FoodListType): LiveData<List<FoodWithIngredients>> {
        return foodDao.getFoodsByType(type)
    }

    suspend fun insertFoodAndIngredients(
        foodList: List<Food>,
        ingredientList: List<FoodIngredients>,
    ) {
        foodDao.insertFoodsWithIngredients(foodList, ingredientList)
    }
}