package com.tekinumut.eruyemekhane.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.tekinumut.eruyemekhane.data.FoodDatabase
import com.tekinumut.eruyemekhane.data.local.FoodDao
import com.tekinumut.eruyemekhane.utils.DataStoreManager
import com.tekinumut.eruyemekhane.utils.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFoodDatabase(app: Application): FoodDatabase =
        Room.databaseBuilder(app, FoodDatabase::class.java, "food_database")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideFoodDao(foodDatabase: FoodDatabase): FoodDao = foodDatabase.foodDao()

    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManager(appContext)

    @Singleton
    @Provides
    fun providePreferencesManager(@ApplicationContext appContext: Context): PreferencesManager =
        PreferencesManager(appContext)

}