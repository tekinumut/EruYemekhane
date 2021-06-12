package com.tekinumut.eruyemekhane.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients
import com.tekinumut.eruyemekhane.data.model.FoodWithIngredients

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(foodList: List<Food>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodIngredients(ingredientList: List<FoodIngredients>)

    @Transaction
    @Query("SELECT * FROM Food")
    fun getFoodsWithIngredients(): LiveData<List<FoodWithIngredients>>

    /**
     * clear Foods before insert.
     */
    @Transaction
    suspend fun insertFoodsWithIngredients(
        foodList: List<Food>,
        ingredientList: List<FoodIngredients>
    ) {
        removeAllFoods()
        insertFood(foodList)
        insertFoodIngredients(ingredientList)
    }

    /**
     * This will remove all Food data and related to Food by Foreign Key
     */
    @Query("Delete from Food")
    suspend fun removeAllFoods()
}