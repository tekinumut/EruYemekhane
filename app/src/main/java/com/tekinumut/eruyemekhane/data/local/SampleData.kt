package com.tekinumut.eruyemekhane.data.local

import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients

object SampleData {

    val studentFoodList  = listOf(
        Food("11 Jun", "1235 Cal", FoodListType.STUDENT, id = 1),
        Food("12 Jun", "3235 Cal", FoodListType.STUDENT, id = 2)
    )
    val personalFoodList = listOf(
        Food("11 Jun", "1235 Cal", FoodListType.PERSONAL, id = 3),
        Food("12 Jun", "3235 Cal", FoodListType.PERSONAL, id = 4)
    )
    val studentIngredientsList = listOf(
        FoodIngredients("Chicken", "220 Cal", foodCreatorId = 1, id = 1),
        FoodIngredients("Chicken2", "222 Cal", foodCreatorId = 1, id = 2)
    )
    val personalIngredientsList = listOf(
        FoodIngredients("Chicken3", "223 Cal", foodCreatorId = 2, id = 3),
        FoodIngredients("Chicken4", "224 Cal", foodCreatorId = 2, id = 4)
    )

    const val personalFoodHtml = "<div class=\"yemekListesiBlok\">\n" +
            " <div class=\"d-title\">Personel Menüsü</div>\n" +
            " <div class=\"row clearfix\">\n" +
            "  <ul class=\"component--job-items mt-2\" style=\"width: 95%;\">\n" +
            "   <li class=\"menu\">\n" +
            "    <a href=\"javascript:;\">\n" +
            "     <span class=\"detail\">\n" +
            "      <span class=\"title\">14 Haziran 2021 Pazartesi <span style=\"display: inline-block; font-size: 12px; font-weight: normal;\">Öğlen</span></span>\n" +
            "      <span class=\"position\">\n" +
            "       <ul>\n" +
            "        <li style=\"list-style: circle;\">ETLİ NOHUT <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">227 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">SEBZELİ BULGUR PİLAVI <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">364 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">KARIŞIK TURŞU <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">24 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">MEYVE <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">120 Kalori</span></li>\n" +
            "       </ul>\n" +
            "      </span>\n" +
            "     </span>\n" +
            "     <span class=\"location hidden-xs\">\n" +
            "      <img src=\"/img/icon-location.svg\" alt=\"\" />\n" +
            "      735\n" +
            "      <span style=\"display: inline-block; font-size: 9px; font-weight: normal;\"> Kalori</span>\n" +
            "     </span>\n" +
            "    </a>\n" +
            "   </li>\n" +
            "   <li class=\"menu\">\n" +
            "    <a href=\"javascript:;\">\n" +
            "     <span class=\"detail\">\n" +
            "      <span class=\"title\">15 Haziran 2021 Salı <span style=\"display: inline-block; font-size: 12px; font-weight: normal;\">Öğlen</span></span>\n" +
            "      <span class=\"position\">\n" +
            "       <ul>\n" +
            "        <li style=\"list-style: circle;\">ETLİ ANKARA TAVA <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">616 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">ZYT KARNABAHAR <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">169 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">HAYDARİ <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">52 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">MEYVE <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">120 Kalori</span></li>\n" +
            "       </ul>\n" +
            "      </span>\n" +
            "     </span>\n" +
            "     <span class=\"location hidden-xs\">\n" +
            "      <img src=\"/img/icon-location.svg\" alt=\"\" />\n" +
            "      957\n" +
            "      <span style=\"display: inline-block; font-size: 9px; font-weight: normal;\"> Kalori</span>\n" +
            "     </span>\n" +
            "    </a>\n" +
            "   </li>\n" +
            "   <li class=\"menu\">\n" +
            "    <a href=\"javascript:;\">\n" +
            "     <span class=\"detail\">\n" +
            "      <span class=\"title\">16 Haziran 2021 Çarşamba <span style=\"display: inline-block; font-size: 12px; font-weight: normal;\">Öğlen</span></span>\n" +
            "      <span class=\"position\">\n" +
            "       <ul>\n" +
            "        <li style=\"list-style: circle;\">SEBZELİ TAVUK SOTE <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">329 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">SOSLU MAKARNA <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">328 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">ÇOBAN SALATA <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">45 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">AŞURE <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">411 Kalori</span></li>\n" +
            "       </ul>\n" +
            "      </span>\n" +
            "     </span>\n" +
            "     <span class=\"location hidden-xs\">\n" +
            "      <img src=\"/img/icon-location.svg\" alt=\"\" />\n" +
            "      1113\n" +
            "      <span style=\"display: inline-block; font-size: 9px; font-weight: normal;\"> Kalori</span>\n" +
            "     </span>\n" +
            "    </a>\n" +
            "   </li>\n" +
            "   <li class=\"menu\">\n" +
            "    <a href=\"javascript:;\">\n" +
            "     <span class=\"detail\">\n" +
            "      <span class=\"title\">17 Haziran 2021 Perşembe <span style=\"display: inline-block; font-size: 12px; font-weight: normal;\">Öğlen</span></span>\n" +
            "      <span class=\"position\">\n" +
            "       <ul>\n" +
            "        <li style=\"list-style: circle;\">COBAN KAVURMA <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">415 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">ŞEHRİYELİ PİRİNÇ PİLAVI <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">380 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">YEŞİL SALATA <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">45 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">MEYVE <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">120 Kalori</span></li>\n" +
            "       </ul>\n" +
            "      </span>\n" +
            "     </span>\n" +
            "     <span class=\"location hidden-xs\">\n" +
            "      <img src=\"/img/icon-location.svg\" alt=\"\" />\n" +
            "      960\n" +
            "      <span style=\"display: inline-block; font-size: 9px; font-weight: normal;\"> Kalori</span>\n" +
            "     </span>\n" +
            "    </a>\n" +
            "   </li>\n" +
            "   <li class=\"menu\">\n" +
            "    <a href=\"javascript:;\">\n" +
            "     <span class=\"detail\">\n" +
            "      <span class=\"title\">18 Haziran 2021 Cuma <span style=\"display: inline-block; font-size: 12px; font-weight: normal;\">Öğlen</span></span>\n" +
            "      <span class=\"position\">\n" +
            "       <ul>\n" +
            "        <li style=\"list-style: circle;\">PİLAV ÜSTÜ TAVUK DÖNER <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">678 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">YEŞİL MERCİMEK SALATA <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">128 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">AYRAN <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">70 Kalori</span></li>\n" +
            "        <li style=\"list-style: circle;\">BAKLAVA <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">480 Kalori</span></li>\n" +
            "       </ul>\n" +
            "      </span>\n" +
            "     </span>\n" +
            "     <span class=\"location hidden-xs\">\n" +
            "      <img src=\"/img/icon-location.svg\" alt=\"\" />\n" +
            "      1356\n" +
            "      <span style=\"display: inline-block; font-size: 9px; font-weight: normal;\"> Kalori</span>\n" +
            "     </span>\n" +
            "    </a>\n" +
            "   </li>\n" +
            "  </ul>\n" +
            " </div>\n" +
            "</div>\n"
}