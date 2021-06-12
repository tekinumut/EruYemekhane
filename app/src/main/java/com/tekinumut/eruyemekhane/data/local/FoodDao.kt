package com.tekinumut.eruyemekhane.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients
import com.tekinumut.eruyemekhane.data.model.FoodWithIngredients

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(foodList: List<Food>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodIngredients(ingredientList: List<FoodIngredients>)

    /**
     * This will remove all Food data and related to Food by Foreign Key
     */
    @Query("Delete from Food where type =:type")
    suspend fun deleteFoodByType(type: FoodListType)

    // Above methods are not in use

    @Transaction
    @Query("SELECT * FROM Food where type = :type")
    fun getFoodsByType(type: FoodListType): LiveData<List<FoodWithIngredients>>

    /**
     * clear Foods before insert.
     */
    @Transaction
    suspend fun insertFoodsWithIngredients(
        foodList: List<Food>,
        ingredientList: List<FoodIngredients>,
    ) {
        val isStudent = foodList.any { it.type == FoodListType.STUDENT }
        val isPersonal = foodList.any { it.type == FoodListType.STUDENT }
        if (isStudent) deleteFoodByType(FoodListType.STUDENT)
        if (isPersonal) deleteFoodByType(FoodListType.PERSONAL)

        insertFood(foodList)
        insertFoodIngredients(ingredientList)
    }

}