package com.tekinumut.eruyemekhane.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.tekinumut.eruyemekhane.data.FoodDatabase
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients
import com.tekinumut.eruyemekhane.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FoodDaoTest {
    private lateinit var foodDatabase: FoodDatabase
    private lateinit var foodDao: FoodDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // inMemoryDatabaseBuilder means it create temporary database
        foodDatabase = Room.inMemoryDatabaseBuilder(context, FoodDatabase::class.java)
            .allowMainThreadQueries()
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
        foodDao = foodDatabase.foodDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        foodDatabase.close()
    }

    // runBlockingTest does not support Transaction
    @Test
    fun insertFoodsWithIngredientsTest() = runBlocking {
        val ingredients1Jun = listOf(ingredientsList[0], ingredientsList[1])
        foodDao.insertFoodsWithIngredients(foodList, ingredientsList)
        val getFoodWithIngredients = foodDao.getFoodsByType(FoodListType.STUDENT).getOrAwaitValue()
        assertThat(getFoodWithIngredients[0].foodIngredients).isEqualTo(ingredients1Jun)
    }

    @Test
    fun everyInsertShouldDeletePreviousFoodData() = runBlocking {
        foodDao.insertFoodsWithIngredients(foodList, ingredientsList)
        foodDao.insertFoodsWithIngredients(foodList, ingredientsList)
        foodDao.insertFoodsWithIngredients(foodList, ingredientsList)
        val getFoodWithIngredients = foodDao.getFoodsByType(FoodListType.STUDENT).getOrAwaitValue()
        val studentFoodSize = foodList.filter { it.type == FoodListType.STUDENT }.size
        assertThat(getFoodWithIngredients.size).isEqualTo(studentFoodSize)
    }

    @Test
    fun deleteFoodTableAlsoDeleteIngredientTableBecForeignKey() = runBlocking {
        foodDao.insertFoodsWithIngredients(foodList, ingredientsList)
        foodDao.deleteFoodByType(FoodListType.STUDENT)
        val foodWithIngredients = foodDao.getFoodsByType(FoodListType.STUDENT).getOrAwaitValue()
        assertThat(foodWithIngredients.firstOrNull()).isNull()
    }

    @Test
    fun deleteFoodByTypeShouldNotDeleteOtherType() = runBlocking {
        foodDao.insertFoodsWithIngredients(foodList, ingredientsList)
        // Remove Personal Type
        foodDao.deleteFoodByType(FoodListType.PERSONAL)
        val foodWithIngredients = foodDao.getFoodsByType(FoodListType.STUDENT).getOrAwaitValue()
        val hasStudentType = foodWithIngredients.any { it.food.type == FoodListType.STUDENT }
        assertThat(hasStudentType).isTrue()
    }

    @Test
    // If we add personal type food all foods has personal type should be removed
    fun insertedFood_ShouldDeleteOnly_InsertedTypeFoods() = runBlocking {
        // now we have student and personal type foods
        foodDao.insertFoodsWithIngredients(foodList, ingredientsList)
        // Add new Student Food
        val newFood = listOf(Food("3 Jun", "1003 Cal", FoodListType.STUDENT, id = 3))
        val newIngredients = listOf(
            FoodIngredients("Chicken3", "103 Cal", foodCreatorId = 3)
        )
        foodDao.insertFoodsWithIngredients(newFood, newIngredients)
        val foodWithIngredients = foodDao.getFoodsByType(FoodListType.STUDENT).getOrAwaitValue()
        // Because all previous student food data have been removed
        // New data size should equal to newFood data we just added
        assertThat(foodWithIngredients.size).isEqualTo(newFood.size)
    }

    private val foodList = listOf(
        Food("1 Jun", "1001 Cal", FoodListType.STUDENT, id = 1),
        Food("2 Jun", "1002 Cal", FoodListType.PERSONAL, id = 2)
    )

    private val ingredientsList = listOf(
        FoodIngredients("Chicken1", "101 Cal", foodCreatorId = 1, id = 1),
        FoodIngredients("Chicken2", "102 Cal", foodCreatorId = 1, id = 2),
        FoodIngredients("Chicken3", "103 Cal", foodCreatorId = 2, id = 3),
        FoodIngredients("Chicken4", "104 Cal", foodCreatorId = 2, id = 4),
    )

}