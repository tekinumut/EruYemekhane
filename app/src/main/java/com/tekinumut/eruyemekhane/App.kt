package com.tekinumut.eruyemekhane

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.tekinumut.eruyemekhane.utils.DataStoreManager
import com.tekinumut.eruyemekhane.utils.DateUtils
import com.tekinumut.eruyemekhane.utils.PreferencesManager
import com.tekinumut.eruyemekhane.utils.Utility
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var preferencesManager: PreferencesManager

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate() {
        super.onCreate()
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)
        Utility.setNightTheme(this, preferencesManager.selectedNightTheme())
        checkRewardAdExpired()
    }

    private fun checkRewardAdExpired() = runBlocking {
        val rewardExpireTime = dataStoreManager.rewardExpireTime.first()
        if (DateUtils.isGivenTimePassed(rewardExpireTime)) {
            preferencesManager.setRemoveBannerChecked(true)
        }
    }
}