package com.Mtkn.umutt.eruyemekhane

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class MainApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        val mainPref = PreferenceManager.getDefaultSharedPreferences(base)
        if (mainPref.getBoolean("nightMode", true))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

}