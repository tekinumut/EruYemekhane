package com.tekinumut.eruyemekhane.utils

import com.google.common.truth.Truth
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.local.SampleData

import org.jsoup.Jsoup
import org.junit.Test

class HtmlParserUnitTest {

    private val html = SampleData.htmlOfFoodListBlock
    private val doc = Jsoup.parse(html)
    private val studentFoodList = doc.getFoodList(FoodListType.STUDENT)
    private val ingredientsListOfFood = doc.getIngredientListOfFoodList(studentFoodList)

//    @Test
//    fun getDateListFromHtml() {
//        val doc = Jsoup.parse(html)
//        Truth.assertThat(doc.getDateList()).isEqualTo(SampleData.htmlOfDateResult)
//    }
//
//    @Test
//    fun getTotalCaloriesListFromHtml() {
//        val doc = Jsoup.parse(html)
//        Truth.assertThat(doc.getTotalCaloriesList()).isEqualTo(SampleData.htmlOfTotalCaloriesResult)
//    }

    @Test
    fun getFoodListFromHtml() {
        Truth.assertThat(studentFoodList).isEqualTo(SampleData.htmlOfStudentFoodList)
    }

    @Test
    fun getFoodIngredientListFromHtml() {
        Truth.assertThat(ingredientsListOfFood).isEqualTo(SampleData.htmlOfFoodIngredientListOfFood)

    }
}