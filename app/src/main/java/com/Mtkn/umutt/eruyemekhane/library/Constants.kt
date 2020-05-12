package com.Mtkn.umutt.eruyemekhane.library

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

    }
}