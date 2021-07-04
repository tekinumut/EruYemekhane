package com.tekinumut.eruyemekhane.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "main")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val mainDataStore = appContext.dataStore

    suspend fun setLastInsertedId(lastId: Long) {
        mainDataStore.edit { main ->
            main[LAST_INSERTED_ID] = lastId
        }
    }

    fun lastInsertedId(): Long = runBlocking {
        mainDataStore.data.map { preferences ->
            preferences[LAST_INSERTED_ID] ?: Constants.DEFAULT_FOOD_ID
        }.first()
    }

    suspend fun setShowBannerAd(isShow: Boolean) {
        mainDataStore.edit { main ->
            main[SHOW_BANNER_AD] = isShow
        }
    }

    val isShowBannerAd: Flow<Boolean> = mainDataStore.data.map { preferences ->
        preferences[SHOW_BANNER_AD] ?: true
    }

    val isShowBannerAd2: Boolean = runBlocking {
        mainDataStore.data.first()[SHOW_BANNER_AD] ?: false
    }

    companion object {
        private val LAST_INSERTED_ID = longPreferencesKey("last_inserted_id")
        private val SHOW_BANNER_AD = booleanPreferencesKey("show_banner_ad")
    }
}