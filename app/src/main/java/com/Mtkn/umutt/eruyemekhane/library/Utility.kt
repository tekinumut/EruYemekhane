package com.Mtkn.umutt.eruyemekhane.library

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.Mtkn.umutt.eruyemekhane.R
import com.Mtkn.umutt.eruyemekhane.YemekModel
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.*
import kotlin.collections.ArrayList

class Utility {
    companion object {
        // Öğrenci listesi tarih verileri
        private var ogrTarihPath = ".ListeSatirOgr >.YemekTarih"

        // Öğrenci listesi yemek verileri
        private var ogrYemekPath = ".ListeSatirOgr >.YemekListe"

        // Personel listesi tarih verileri
        private var perTarihPath = ".Personel > .ListeSatir > .YemekTarih"

        // Personel listesi yemek verileri
        private var perYemekPath = ".Personel > .ListeSatir > .YemekListe"

        // Mevcut yemek dizisindeki her elemana erişmemizi sağlar.
        private var yemekEachPath = ">ul >li"

        /**
         * Personel ve Öğrenci yemek listesinin birleşimini dönderir.
         * @param type -1 tüm listeyi dönderir.
         *              0 öğrenci listesini dönderir.
         *              1 personel listesini dönderir.
         */
        fun getSpecificList(doc: Document, type: Int): List<YemekModel> {
            val ogrTarihValue: Elements = doc.select(ogrTarihPath)
            val ogrYemekValue: Elements = doc.select(ogrYemekPath)
            val perTarihValue: Elements = doc.select(perTarihPath)
            val perYemekValue: Elements = doc.select(perYemekPath)

            val ogrList = populateList(ogrTarihValue, ogrYemekValue, Constants.ogrType)
            val perList = populateList(perTarihValue, perYemekValue, Constants.perType)
            return when (type) {
                Constants.ogrType -> ogrList
                Constants.perType -> perList
                else -> ogrList + perList
            }
        }

        /**
         * İlgili gruba ait yemek ve tarih bilgilerini listeye doldurur.
         */
        private fun populateList(tarihPath: Elements, yemekPath: Elements, type: Int): ArrayList<YemekModel> {
            val modelList = ArrayList<YemekModel>()
            tarihPath.forEachIndexed { i, tarih ->
                val yemekler = StringBuilder()
                yemekPath[i].select(yemekEachPath).forEach {
                    yemekler.append("\u2022 ").append(it.text()).append("\n").toString()
                }
                modelList.add(YemekModel(null, tarih.text(), yemekler.toString(), type))
            }
            return modelList
        }

        /**
         * Cihazda internet olup olmadığını denetler.
         *
         * @return Cihazda internetin olup olmadığı durumu.
         **/
        fun isInternetAvailable(context: Context): Boolean {
            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                result = when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                @Suppress("DEPRECATION")
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }
                    }
                }
            }
            return result
        }

        /**
         * Gece ve gündüz modu seçimini yapar
         * @param type -1 -> Cihaz seçimini kullanır
         *              0 -> Gündüz modu
         *              1 -> Gece modu
         */
        fun setTheme(type: Int) {
            when (type) {
                -1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        /**
         * Yükleniyor dialog penceresini dönderir.
         */
        fun getLoadingDialog(activity: Activity): AlertDialog = AlertDialog.Builder(activity)
            .setCancelable(false)
            .setView(R.layout.dialog_loading)
            .create()

        /**
         * Listelerin alındığı web sayfasını açar
         */
        fun openListWebSite(activity: Activity) {
            val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
            builder.setStartAnimations(activity, R.anim.slide_in_right, R.anim.slide_out_left)
            builder.setExitAnimations(activity, R.anim.slide_in_left, R.anim.slide_out_right)

            val tabIntent = builder.build()
            tabIntent.launchUrl(activity, Uri.parse(Constants.URL))

        }

        fun timeStampToDateString(timeStamp: Long): String = Constants.defaultSDF.format(Date(timeStamp))


        fun isRewardAdTimeExpired(context: Context): Boolean {
            val secondPref = SecondPref.getInstance(context)
            val expireTimeStamp = secondPref.getLong(context.getString(R.string.prefRewardExpireDate), Constants.defRewardExpireDate.time)
            return expireTimeStamp <= Constants.currentTimeStamp()
        }

    }
}