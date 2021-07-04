package com.tekinumut.eruyemekhane.ui.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.tekinumut.eruyemekhane.R
import com.tekinumut.eruyemekhane.utils.Utility

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var nightTheme: ListPreference
    private lateinit var updateListOnLaunch: SwitchPreferenceCompat
    private lateinit var ads: SwitchPreferenceCompat

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey)
        init()
        setListeners()
    }

    private fun init() {
        nightTheme = findPreference(getString(R.string.night_theme_key))!!
        updateListOnLaunch = findPreference(getString(R.string.update_list_on_launch_key))!!
        ads = findPreference(getString(R.string.ads_key))!!
    }

    private fun setListeners() {
        nightTheme.setOnPreferenceChangeListener { _, newValue ->
            Utility.setNightTheme(nightTheme.context, newValue.toString())
            true
        }
    }

}