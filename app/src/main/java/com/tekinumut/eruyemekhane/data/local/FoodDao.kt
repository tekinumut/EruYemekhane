package com.tekinumut.eruyemekhane.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients
import com.tekinumut.eruyemekhane.data.model.FoodWithIngredients
import com.tekinumut.eruyemekhane.utils.Constants
import com.tekinumut.eruyemekhane.utils.DataStoreManager

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
     * This will remove all Food data and related to Food by Foreign Key
     */
    @Query("Delete from Food where type =:type")
    suspend fun deleteFoodByType(type: FoodListType)

    @Transaction
    suspend fun insertFoodsWithIngredients(
        foodList: List<Food>,
        ingredientList: List<FoodIngredients>,
        foodListType: FoodListType,
        dataStoreManager: DataStoreManager
    ) {
        deleteFoodByType(foodListType)
        val lastId: Long = insertFood(foodList).lastOrNull() ?: Constants.DEFAULT_FOOD_ID
        // prevent empty api list update lastInsertedId
        if (lastId > 0)
            dataStoreManager.setLastInsertedId(lastId)
        insertFoodIngredients(ingredientList)
    }


}