package com.tekinumut.eruyemekhane.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.firebase.perf.metrics.AddTrace
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients
import com.tekinumut.eruyemekhane.data.model.FoodWithIngredients
import com.tekinumut.eruyemekhane.utils.getFoodList
import com.tekinumut.eruyemekhane.utils.getIngredientList
import org.jsoup.nodes.Document

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(foodList: List<Food>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodIngredients(ingredientList: List<FoodIngredients>)

    @Transaction
    @Query("SELECT * FROM Food where type = :type")
    fun getFoodsByType(type: FoodListType): LiveData<List<FoodWithIngredients>>

    /**
     * Remove all Foods and Ingredients of Foods by Foreign Key
     */
    @Query("Delete from Food where type =:type")
    suspend fun deleteFoodByType(type: FoodListType)

    @Transaction
    @AddTrace(name = "insertFoodsWithIngredients")
    suspend fun insertFoodsWithIngredients(document: Document, foodListType: FoodListType) {
        val foodList = document.getFoodList(foodListType)
        // Refresh food and ingredients data of department
        // by deleting and inserting again
        deleteFoodByType(foodListType)
        val currentFoodIdList: List<Long> = insertFood(foodList)
        val ingredientList = document.getIngredientList(foodList, currentFoodIdList)
        insertFoodIngredients(ingredientList)
    }
}