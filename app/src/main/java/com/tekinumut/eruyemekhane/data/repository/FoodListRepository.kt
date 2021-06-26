package com.tekinumut.eruyemekhane.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.tekinumut.eruyemekhane.base.BaseDataSource
import com.tekinumut.eruyemekhane.data.FoodDatabase
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.local.SampleData
import com.tekinumut.eruyemekhane.data.remote.MainApiService
import com.tekinumut.eruyemekhane.utils.networkBoundResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListRepository @Inject constructor(
    private val mainApi: MainApiService,
    private val foodDatabase: FoodDatabase
) : BaseDataSource() {

    private val foodDao = foodDatabase.foodDao()

    fun getFoodList(foodListType: FoodListType, shouldFetch: Boolean = true) = networkBoundResource(
        databaseQuery = { foodDao.getFoodsByType(foodListType) },
        networkCall = {
            safeApiCall { mainApi.getFoodList(foodListType.apiVal) }
        },
        saveCallResult = { htmlResponse ->
            // TODO() change data by htmlResponse
            Log.e("BaseApp", "html: ${htmlResponse.substring(0, 100)} ")
            val foodList = SampleData.studentFoodList
            val ingredientList = SampleData.studentIngredientsList
            foodDatabase.withTransaction {
                foodDao.deleteFoodByType(foodListType)
                foodDao.insertFood(foodList)
                foodDao.insertFoodIngredients(ingredientList)
            }
        }
    )
}