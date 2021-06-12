package com.tekinumut.eruyemekhane.di

import com.tekinumut.eruyemekhane.data.local.FoodDao
import com.tekinumut.eruyemekhane.data.remote.MainApiService
import com.tekinumut.eruyemekhane.data.repository.FoodListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideFoodListRepo(mainApiService: MainApiService, foodDao: FoodDao): FoodListRepository {
        return FoodListRepository(mainApiService, foodDao)
    }
}