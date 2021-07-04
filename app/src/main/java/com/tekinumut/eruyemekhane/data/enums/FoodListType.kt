package com.tekinumut.eruyemekhane.data.enums

import androidx.annotation.StringRes
import com.tekinumut.eruyemekhane.R
import com.tekinumut.eruyemekhane.utils.ConstantsApi

enum class FoodListType(val apiUrl: String, @StringRes val nameRes: Int) {
    STUDENT(ConstantsApi.STUDENT_URL, R.string.student_list_title),
    PERSONAL(ConstantsApi.PERSONAL_URL, R.string.personal_list_title)

}