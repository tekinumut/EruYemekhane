package com.tekinumut.eruyemekhane.data.repository

import com.tekinumut.eruyemekhane.base.BaseDataSource
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.local.FoodDao
import com.tekinumut.eruyemekhane.data.remote.MainApiService
import com.tekinumut.eruyemekhane.utils.networkBoundResource
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListRepository @Inject constructor(
    private val mainApi: MainApiService,
    private val foodDao: FoodDao
) : BaseDataSource() {

    fun getFoodList(foodListType: FoodListType, shouldFetch: Boolean) = networkBoundResource(
        databaseQuery = { foodDao.getFoodsByType(foodListType) },
        networkCall = {
            safeApiCall { mainApi.getFoodList(foodListType.apiUrl) }
        },
        saveCallResult = { htmlResponse ->
            val document = Jsoup.parse(htmlResponse)
            foodDao.insertFoodsWithIngredients(document, foodListType)
        },
        shouldFetch = { shouldFetch }
    )

}