package com.tekinumut.eruyemekhane.utils

import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients
import org.jsoup.nodes.Document

// [28 Haziran 2021 Pazartesi Öğlen,29 Haziran 2021 Salı Öğlen]
private const val DATE_LIST_TAG = ".yemekListesiBlok li .title"

// [ETLİ KURU FASULYE 354 Kalori ŞEHRİYELİ PİRİNÇ PİLAVI 380 Kalori MEYVE 120 Kalori AYRAN 70 Kalori,
//  SEBZELİ ET SOTE 449 Kalori MERCİMEKLİ BULGUR PİLAVI 338 Kalori YOĞURT 136 Kalori YEŞİL SALATA 45 Kalori]
private const val FOOD_INGREDIENTS_TAG = ".yemekListesiBlok li ul"

// [924 Kalori, 968 Kalori]
private const val TOTAL_CALORIE_TAG = ".yemekListesiBlok .location.hidden-xs"

private fun Document.getDateList(): List<String> {
    val dateList = mutableListOf<String>()
    val dateElements = select(DATE_LIST_TAG)
    dateElements.forEach { dateList.add(it.ownText()) }
    return dateList
}

private fun Document.getTotalCaloriesList(): List<String> {
    val totalCaloriesList = mutableListOf<String>()
    val totalCaloriesElements = select(TOTAL_CALORIE_TAG)
    totalCaloriesElements.forEach { totalCaloriesList.add(it.text()) }
    return totalCaloriesList
}

fun Document.getFoodList(type: FoodListType): List<Food> {
    val foodList = mutableListOf<Food>()
    getDateList().forEachIndexed { index, date ->
        val totalCalories = getTotalCaloriesList().getOrNull(index)
        foodList.add(Food(null, date, totalCalories, type))
    }
    return foodList
}

fun Document.getIngredientList(foodList: List<Food>, idList: List<Long>): List<FoodIngredients> {
    val foodIngredientList = mutableListOf<FoodIngredients>()
    val foodIngredientElements = select(FOOD_INGREDIENTS_TAG)
    foodList.forEachIndexed { index, _ ->
        foodIngredientElements[index].select("li").forEach {
            foodIngredientList.add(
                FoodIngredients(it.ownText(), it.select("span").text(), idList[index])
            )
        }
    }
    return foodIngredientList
}
