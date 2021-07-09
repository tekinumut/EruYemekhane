package com.tekinumut.eruyemekhane.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.tekinumut.eruyemekhane.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun updateListOnLaunch(): Boolean {
        return pref.getBoolean(context.getString(R.string.update_list_on_launch_key), true)
    }

    fun selectedNightTheme(): String {
        val defaultTheme = context.getString(R.string.night_theme_value_system_default)
        return pref.getString(context.getString(R.string.night_theme_key), defaultTheme)!!
    }

    fun setRemoveBannerChecked(isChecked: Boolean) {
        pref.edit().putBoolean(context.getString(R.string.removeBanner_key), isChecked).apply()
    }

    fun isRemoveBannerChecked(): Boolean {
        return pref.getBoolean(context.getString(R.string.removeBanner_key), true)
    }
}