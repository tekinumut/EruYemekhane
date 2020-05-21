package com.Mtkn.umutt.eruyemekhane.library

import java.text.SimpleDateFormat
import java.util.*

class Constants {
    companion object {

        // Yemek liste verilerinin alınacağı web adresi
        const val URL: String = "https://www.erciyes.edu.tr/kategori/KAMPUSTE-YASAM/Yemek-Hizmetleri/22/167"

        // Öğrenci listesini belirten tip değeri
        var ogrType = 0

        // Personel listesini belirten tip değeri
        const val perType = 1

        // Hem öğrenci hem personel listesini belirten tip değeri
        const val bothType = -1

        // Ayarlar'dan Ana sayfaya dönerken, Ayarlarda bulunan hangi sayfadan dönüldüğünü
        // belirten intent'in key anahtarı
        const val settingsPageKey = "settingsPageKey"

        // Varsayılan zaman formatı
        val defaultSDF: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH)

        // Varsayılan reklam bitim zamanı
        private const val defRewardExpireDateAsString = "01-01-1970 00:00:00"

        // Varsayılan reklam bitim zamanı
        val defRewardExpireDate = defaultSDF.parse(defRewardExpireDateAsString)!!

        // Şuanki zamanı getirir
        fun currentTimeStamp(): Long = defaultSDF.parse(defaultSDF.format(Date()))!!.time

        // 1 ay sonraya ötelenmiş zamanı getirir
        fun nextOneMonthTimeStamp(): Long {
            val oneMonth: Long = 60 * 60 * 24 * 30
            return currentTimeStamp() + oneMonth * 1000
        }

    }
}