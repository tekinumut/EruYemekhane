package com.Mtkn.umutt.eruyemekhane

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.Mtkn.umutt.eruyemekhane.library.Utility

class MainApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        val mainPref = PreferenceManager.getDefaultSharedPreferences(base)
        val typeOfNightMode = mainPref.getString(getString(R.string.nightModeKey), "-1")?.toInt() ?: -1
        Utility.setTheme(typeOfNightMode)
    }
}