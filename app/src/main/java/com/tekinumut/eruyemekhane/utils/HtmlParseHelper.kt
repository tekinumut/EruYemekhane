package com.tekinumut.eruyemekhane.utils

import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients
import org.jsoup.nodes.Document

// eg: [28 Haziran 2021 Pazartesi Öğlen,29 Haziran 2021 Salı Öğlen]
// collects Date List
private const val DATE_LIST_TAG = ".yemekListesiBlok li:has(ul) .title"

// [ETLİ KURU FASULYE 354 Kalori ŞEHRİYELİ PİRİNÇ PİLAVI 380 Kalori MEYVE 120 Kalori AYRAN 70 Kalori,
//  SEBZELİ ET SOTE 449 Kalori MERCİMEKLİ BULGUR PİLAVI 338 Kalori YOĞURT 136 Kalori YEŞİL SALATA 45 Kalori]
// collects Ingredients of Food
private const val FOOD_INGREDIENTS_TAG = ".yemekListesiBlok li ul"

// [924 Kalori, 968 Kalori]
private const val TOTAL_CALORIE_TAG = ".yemekListesiBlok .location.hidden-xs"

/**
 * Get published menu dates
 * eg:12 Temmuz 2021 Pazartesi
 *    13 Temmuz 2021 Salı
 *    14 Temmuz 2021 Çarşamba
 */
private fun Document.getDateList(): List<String> {
    val dateList = mutableListOf<String>()
    val dateElements = select(DATE_LIST_TAG)
    dateElements.forEach { dateList.add(it.text()) }
    return dateList
}

/**
 * Get total calories menu of the day
 * eg: 914 Kalori
 * eg: 742 Kalori
 * eg: 750 Kalori
 */
private fun Document.getTotalCaloriesList(): List<String> {
    val totalCaloriesList = mutableListOf<String>()
    val totalCaloriesElements = select(TOTAL_CALORIE_TAG)
    totalCaloriesElements.forEach { totalCaloriesList.add(it.text()) }
    return totalCaloriesList
}

/**
 * Get menu with given date and total calorie.
 * eg: Food(date=12 Temmuz 2021 Pazartesi, totalCalories=914 Kalori, type=STUDENT)
 */
fun Document.getFoodList(type: FoodListType): List<Food> {
    val foodList = mutableListOf<Food>()
    getDateList().forEachIndexed { index, date ->
        val totalCalories = getTotalCaloriesList().getOrNull(index)
        foodList.add(Food(null, date, totalCalories, type))
    }
    return foodList
}

/**
 * Get ingredients list of menus of the day
 */
fun Document.getIngredientList(
    foodList: List<Food>,
    currentFoodIdList: List<Long>
): List<FoodIngredients> {
    val foodIngredientList = mutableListOf<FoodIngredients>()
    val foodIngredientElements = select(FOOD_INGREDIENTS_TAG)
    foodList.forEachIndexed { index, _ ->
        foodIngredientElements[index].select("li").forEach {
            foodIngredientList.add(
                FoodIngredients(it.ownText(), it.select("span").text(), currentFoodIdList[index])
            )
        }
    }
    return foodIngredientList
}
