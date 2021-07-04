package com.tekinumut.eruyemekhane.data.repository

import com.tekinumut.eruyemekhane.base.BaseDataSource
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.local.FoodDao
import com.tekinumut.eruyemekhane.data.remote.MainApiService
import com.tekinumut.eruyemekhane.utils.DataStoreManager
import com.tekinumut.eruyemekhane.utils.getFoodList
import com.tekinumut.eruyemekhane.utils.getIngredientListOfFoodList
import com.tekinumut.eruyemekhane.utils.networkBoundResource
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListRepository @Inject constructor(
    private val mainApi: MainApiService,
    private val foodDao: FoodDao,
    private val dataStoreManager: DataStoreManager
) : BaseDataSource() {

    fun getFoodList(foodListType: FoodListType, shouldFetch: Boolean) = networkBoundResource(
        databaseQuery = { foodDao.getFoodsByType(foodListType) },
        networkCall = {
            safeApiCall { mainApi.getFoodList(foodListType.apiUrl) }
        },
        saveCallResult = { htmlResponse ->
            val doc = Jsoup.parse(htmlResponse)
            val lastId = dataStoreManager.lastInsertedId()
            val foodList = doc.getFoodList(foodListType, lastId)
            val ingredientList = doc.getIngredientListOfFoodList(foodList)
            foodDao.insertFoodsWithIngredients(
                foodList,
                ingredientList,
                foodListType,
                dataStoreManager
            )
        },
        shouldFetch = { shouldFetch }
    )

}