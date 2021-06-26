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

    @Test
    fun insertFoodsTest() = runBlocking {
        val studentFoodList = SampleData.studentFoodList
        foodDao.insertFood(studentFoodList)
        val observeFoodList = foodDao.getFoodsByType(FoodListType.STUDENT).getOrAwaitValue()
        assertThat(observeFoodList[0].food.id).isEqualTo(studentFoodList[0].id)
    }

    @Test
    fun insertFoodsWithIngredientsTest() = runBlocking {
        val studentFoodList = SampleData.studentFoodList
        val studentIngredientsList = SampleData.studentIngredientsList
        foodDao.insertFood(studentFoodList)
        foodDao.insertFoodIngredients(studentIngredientsList)
        val observeFoodIngredients = foodDao.getFoodsByType(FoodListType.STUDENT).getOrAwaitValue()
        assertThat(observeFoodIngredients[0].food.id).isEqualTo(studentFoodList[0].id)
        assertThat(observeFoodIngredients[0].foodIngredients).isEqualTo(studentIngredientsList)
    }

    @Test
    fun deleteFoodShouldDeleteType() = runBlocking {
        // Get student type size of list
        val studentFoodSize =
            SampleData.studentFoodList.filter { it.type == FoodListType.STUDENT }.size
        foodDao.insertFood(SampleData.studentFoodList)
        foodDao.deleteFoodByType(FoodListType.STUDENT)
        val observeFoodList = foodDao.getFoodsByType(FoodListType.STUDENT).getOrAwaitValue()
        assertThat(observeFoodList.size).isNotEqualTo(studentFoodSize)
    }

    @Test
    fun deleteFoodByTypeShouldNotDeleteOtherType() = runBlocking {
        foodDao.insertFood(SampleData.studentFoodList)
        foodDao.insertFood(SampleData.personalFoodList)
        // Remove Personal Type
        foodDao.deleteFoodByType(FoodListType.PERSONAL)
        val foodWithIngredients = foodDao.getFoodsByType(FoodListType.STUDENT).getOrAwaitValue()
        val hasStudentType = foodWithIngredients.any { it.food.type == FoodListType.STUDENT }
        assertThat(hasStudentType).isTrue()
    }

    @Test
    fun deleteFoodTableAlsoDeleteIngredientTableBecForeignKey() = runBlocking {
        foodDao.insertFood(SampleData.studentFoodList)
        foodDao.insertFoodIngredients(SampleData.studentIngredientsList)
        foodDao.deleteFoodByType(FoodListType.STUDENT)
        val foodWithIngredients = foodDao.getFoodsByType(FoodListType.STUDENT).getOrAwaitValue()
        assertThat(foodWithIngredients.firstOrNull()).isNull()
    }

}