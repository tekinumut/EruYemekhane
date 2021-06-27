package com.tekinumut.eruyemekhane.data.repository

import androidx.room.withTransaction
import com.tekinumut.eruyemekhane.base.BaseDataSource
import com.tekinumut.eruyemekhane.data.FoodDatabase
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.remote.MainApiService
import com.tekinumut.eruyemekhane.utils.getFoodList
import com.tekinumut.eruyemekhane.utils.getIngredientListOfFoodList
import com.tekinumut.eruyemekhane.utils.networkBoundResource
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListRepository @Inject constructor(
    private val mainApi: MainApiService,
    private val foodDatabase: FoodDatabase
) : BaseDataSource() {

    private val foodDao = foodDatabase.foodDao()

    fun getFoodList(foodListType: FoodListType) = networkBoundResource(
        databaseQuery = { foodDao.getFoodsByType(foodListType) },
        networkCall = {
            safeApiCall { mainApi.getFoodList(foodListType.apiUrl) }
        },
        saveCallResult = { htmlResponse ->
            val doc = Jsoup.parse(htmlResponse)
            val foodList = doc.getFoodList(foodListType)
            val ingredientList = doc.getIngredientListOfFoodList(foodList)
            foodDatabase.withTransaction {
                foodDao.deleteFoodByType(foodListType)
                foodDao.insertFood(foodList)
                foodDao.insertFoodIngredients(ingredientList)
            }
        }
    )

}