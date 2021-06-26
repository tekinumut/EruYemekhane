package com.tekinumut.eruyemekhane.data.enums

import com.tekinumut.eruyemekhane.utils.ConstantsApi

enum class FoodListType(val apiVal: String) {
    STUDENT(ConstantsApi.studentUrl),
    PERSONAL(ConstantsApi.personalUrl)
}