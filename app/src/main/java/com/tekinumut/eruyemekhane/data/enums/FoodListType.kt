package com.tekinumut.eruyemekhane.data.enums

import com.tekinumut.eruyemekhane.BuildConfig
import com.tekinumut.eruyemekhane.utils.ConstantsApi

enum class FoodListType(val apiUrl: String, val webSiteUrl: String) {
    STUDENT(ConstantsApi.STUDENT_URL, BuildConfig.BASE_URL + ConstantsApi.STUDENT_URL),
    PERSONAL(ConstantsApi.PERSONAL_URL, BuildConfig.BASE_URL + ConstantsApi.PERSONAL_URL)
}