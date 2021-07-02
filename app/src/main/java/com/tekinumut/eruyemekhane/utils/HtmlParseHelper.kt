package com.tekinumut.eruyemekhane.utils

import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients
import org.jsoup.nodes.Document

private fun Document.getDateList(): List<String> {
    val dateList = mutableListOf<String>()
    val dateElements = select(ConstantsApi.DATE_LIST_TAG)
    dateElements.forEach { dateList.add(it.ownText()) }
    return dateList
}

private fun Document.getTotalCaloriesList(): List<String> {
    val totalCaloriesList = mutableListOf<String>()
    val totalCaloriesElements = select(ConstantsApi.TOTAL_CALORIE_TAG)
    totalCaloriesElements.forEach { totalCaloriesList.add(it.text()) }
    return totalCaloriesList
}

fun Document.getFoodList(type: FoodListType, lastId: Long): List<Food> {
    val foodList = mutableListOf<Food>()
    getDateList().forEachIndexed { index, date ->
        val totalCalories = getTotalCaloriesList().getOrNull(index)
        foodList.add(Food(lastId + index + 1, date, totalCalories, type))
    }
    return foodList
}

fun Document.getIngredientListOfFoodList(foodList: List<Food>): List<FoodIngredients> {
    val foodIngredientList = mutableListOf<FoodIngredients>()
    val foodIngredientElements = select(ConstantsApi.FOOD_INGREDIENTS_TAG)
    foodList.forEachIndexed { index, food ->
        foodIngredientElements[index].select("li").forEach {
            foodIngredientList.add(
                FoodIngredients(it.ownText(), it.select("span").text(), food.id)
            )
        }
    }
    return foodIngredientList
}
