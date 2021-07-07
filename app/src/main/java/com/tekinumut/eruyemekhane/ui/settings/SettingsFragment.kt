package com.tekinumut.eruyemekhane.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.tekinumut.eruyemekhane.R
import com.tekinumut.eruyemekhane.ui.settings.removebanner.RemoveBannerDialogFragment
import com.tekinumut.eruyemekhane.utils.DataStoreManager
import com.tekinumut.eruyemekhane.utils.DateUtils
import com.tekinumut.eruyemekhane.utils.DateUtils.toFormattedDate
import com.tekinumut.eruyemekhane.utils.Utility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private lateinit var nightTheme: ListPreference
    private lateinit var updateListOnLaunch: SwitchPreferenceCompat
    private lateinit var removeBannerAd: SwitchPreferenceCompat

    private var rewardAdExpireTime: Long = 0

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey)
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun init() {
        nightTheme = findPreference(getString(R.string.night_theme_key))!!
        updateListOnLaunch = findPreference(getString(R.string.update_list_on_launch_key))!!
        removeBannerAd = findPreference(getString(R.string.removeBanner_key))!!
    }

    private fun setListeners() {
        nightTheme.setOnPreferenceChangeListener { _, newValue ->
            Utility.setNightTheme(nightTheme.context, newValue.toString())
            true
        }
        removeBannerAd.setOnPreferenceChangeListener { _, newValue ->
            val isChecked: Boolean = newValue as Boolean
            if (isChecked) {
                // Switch can be opened in any situation
                removeBannerAd.isChecked = true
            } else {
                if (DateUtils.isGivenTimePassed(rewardAdExpireTime)) {
                    findNavController().navigate(R.id.action_settingsFragment_to_removeBannerDialogFragment)
                } else {
                    // There is still time for disable ad as user wish
                    removeBannerAd.isChecked = false
                }
            }
            // We'll manage status of switch
            false
        }
        setFragmentResultListener(RemoveBannerDialogFragment.REQUEST_KEY) { _, bundle ->
            val isRewardEarned = bundle.getBoolean(RemoveBannerDialogFragment.ON_REWARD_EARNED)
            if (isRewardEarned) {
                removeBannerAd.isChecked = false
            }
        }
        dataStoreManager.rewardExpireTime.asLiveData().observe(viewLifecycleOwner, {
            rewardAdExpireTime = it
            if (DateUtils.isGivenTimePassed(rewardAdExpireTime)) {
                removeBannerAd.isChecked = true
            } else {
                removeBannerAd.summaryOff = getString(
                    R.string.removeBanner_summary_off,
                    rewardAdExpireTime.toFormattedDate()
                )
            }
        })
    }

}