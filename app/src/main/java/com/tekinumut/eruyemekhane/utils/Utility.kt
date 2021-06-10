package com.tekinumut.eruyemekhane.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.tekinumut.eruyemekhane.R

object Utility {
    /**
     * It opens the given url with Chrome custom tabs
     */
    fun openWebSiteWithCustomTabs(context: Context, url: String) {
        val colorSchemeBuilder = CustomTabColorSchemeParams.Builder().run {
            setToolbarColor(ContextCompat.getColor(context, R.color.purple_500))
                .build()
        }
        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder().run {
            setDefaultColorSchemeParams(colorSchemeBuilder)
            setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
            setExitAnimations(context, R.anim.slide_in_left, R.anim.slide_out_right)
        }
        val tabIntent = builder.build()
        tabIntent.launchUrl(context, Uri.parse(url))
    }
}