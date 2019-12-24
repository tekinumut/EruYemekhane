package com.Mtkn.umutt.eruyemekhane

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class Library {

    companion object {

        const val URL: String = "https://www.erciyes.edu.tr/kategori/KAMPUSTE-YASAM/Yemek-Hizmetleri/22/167"

        /**
         * Cihazda internet olup olmadığını denetler.
         *
         * @return Cihazda internetin olup olmadığı durumu.
         **/
        @Suppress("DEPRECATION")
        fun checkInternetConnection(mContext: Context): Boolean {
            var result = false
            val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
                            NetworkCapabilities.TRANSPORT_CELLULAR))
                        result = true
                }
            } else {
                val activeNetwork = cm.activeNetworkInfo
                if (activeNetwork != null) { // connected to the internet
                    if (activeNetwork.type == ConnectivityManager.TYPE_WIFI || activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                        result = true
                }
            }
            return result
        }
    }

}