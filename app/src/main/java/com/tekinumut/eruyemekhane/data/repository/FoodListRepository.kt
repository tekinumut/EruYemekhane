package com.tekinumut.eruyemekhane.data.repository

import com.tekinumut.eruyemekhane.base.BaseDataSource
import com.tekinumut.eruyemekhane.data.service.MainApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodListRepository @Inject constructor(
    private val mainApi: MainApiService
) : BaseDataSource() {

    fun getStudentFoodList() = observeApi { mainApi.getStudentFoodList() }
    fun getPersonalFoodList() = observeApi { mainApi.getStudentFoodList()}
}