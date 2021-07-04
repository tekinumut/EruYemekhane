package com.tekinumut.eruyemekhane

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.tekinumut.eruyemekhane.utils.PreferencesManager
import com.tekinumut.eruyemekhane.utils.Utility
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var preferencesManager: PreferencesManager
    override fun onCreate() {
        super.onCreate()
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)
        Utility.setNightTheme(this, preferencesManager.selectedNightTheme())
    }
}