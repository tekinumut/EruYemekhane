package com.tekinumut.eruyemekhane.data.enums

import androidx.annotation.StringRes
import com.tekinumut.eruyemekhane.R
import com.tekinumut.eruyemekhane.utils.Constants

enum class FoodListType(val apiUrl: String, @StringRes val nameRes: Int) {
    STUDENT(Constants.STUDENT_URL, R.string.student_list_title),
    PERSONAL(Constants.PERSONAL_URL, R.string.personal_list_title)

}