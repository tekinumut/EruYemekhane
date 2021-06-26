package com.tekinumut.eruyemekhane.di

import android.app.Application
import androidx.room.Room
import com.tekinumut.eruyemekhane.data.FoodDatabase
import com.tekinumut.eruyemekhane.data.local.FoodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFoodDatabase(app: Application): FoodDatabase = Room.databaseBuilder(
        app,
        FoodDatabase::class.java,
        "food_database"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideFoodDao(foodDatabase: FoodDatabase): FoodDao = foodDatabase.foodDao()
}