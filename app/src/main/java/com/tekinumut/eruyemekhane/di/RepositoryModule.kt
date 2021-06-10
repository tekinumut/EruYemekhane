package com.tekinumut.eruyemekhane.di

import com.tekinumut.eruyemekhane.data.repository.FoodListRepository
import com.tekinumut.eruyemekhane.data.service.MainApiService
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
    fun provideFoodListRepo(mainApiService: MainApiService): FoodListRepository {
        return FoodListRepository(mainApiService)
    }
}