package com.tekinumut.eruyemekhane.utils

object ConstantsApi {
    const val STUDENT_URL = "ogrenci-yemek-listesi"
    const val PERSONAL_URL = "personel-yemek-listesi"

    // [28 Haziran 2021 Pazartesi Öğlen,29 Haziran 2021 Salı Öğlen]
    const val DATE_LIST_TAG = ".yemekListesiBlok li .title"

    // [ETLİ KURU FASULYE 354 Kalori ŞEHRİYELİ PİRİNÇ PİLAVI 380 Kalori MEYVE 120 Kalori AYRAN 70 Kalori,
    //  SEBZELİ ET SOTE 449 Kalori MERCİMEKLİ BULGUR PİLAVI 338 Kalori YOĞURT 136 Kalori YEŞİL SALATA 45 Kalori]
    const val FOOD_INGREDIENTS_TAG = ".yemekListesiBlok li ul"

    // [924 Kalori, 968 Kalori]
    const val TOTAL_CALORIE_TAG = ".yemekListesiBlok .location.hidden-xs"
}