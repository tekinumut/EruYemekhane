package com.tekinumut.eruyemekhane.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tekinumut.eruyemekhane.data.local.FoodDao
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients

@Database(entities = [Food::class, FoodIngredients::class], version = 1)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}

