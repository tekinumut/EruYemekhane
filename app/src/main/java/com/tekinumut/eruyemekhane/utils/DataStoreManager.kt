package com.tekinumut.eruyemekhane.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "main")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val mainDataStore = appContext.dataStore

    val rewardExpireTime: Flow<Long> = mainDataStore.data.map { preferences ->
        preferences[REWARD_AD_EXPIRE_TIME] ?: 0
    }

    suspend fun setRewardAdExpireTime(expireTime: Long) {
        mainDataStore.edit { preferences ->
            preferences[REWARD_AD_EXPIRE_TIME] = expireTime
        }
    }

    companion object {
        private val REWARD_AD_EXPIRE_TIME = longPreferencesKey("reward_ad_expire_time")
    }
}