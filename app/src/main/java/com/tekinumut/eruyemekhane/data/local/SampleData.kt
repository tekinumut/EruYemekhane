@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.tekinumut.eruyemekhane.data.local

import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.data.model.Food
import com.tekinumut.eruyemekhane.data.model.FoodIngredients

object SampleData {

    val studentFoodList = listOf(
        Food(1, "11 Jun", "1235 Cal", FoodListType.STUDENT),
        Food(2, "12 Jun", "3235 Cal", FoodListType.STUDENT)
    )
    val personalFoodList = listOf(
        Food(3, "11 Jun", "1235 Cal", FoodListType.PERSONAL),
        Food(4, "12 Jun", "3235 Cal", FoodListType.PERSONAL)
    )
    val studentIngredientsList = listOf(
        FoodIngredients("Chicken", "220 Cal", foodCreatorId = 1, id = 1),
        FoodIngredients("Chicken2", "222 Cal", foodCreatorId = 1, id = 2)
    )
    val personalIngredientsList = listOf(
        FoodIngredients("Chicken3", "223 Cal", foodCreatorId = 2, id = 3),
        FoodIngredients("Chicken4", "224 Cal", foodCreatorId = 2, id = 4)
    )
    val htmlOfDateResult = listOf(
        "28 Haziran 2021 Pazartesi",
        "29 Haziran 2021 Salı",
        "30 Haziran 2021 Çarşamba"
    )
    val htmlOfTotalCaloriesResult = listOf(
        "924 Kalori",
        "968 Kalori",
        "891 Kalori"
    )
    val htmlOfFoodIngredientListOfFood = listOf(
        FoodIngredients("ETLİ KURU FASULYE", "354 Kalori", 1, 0),
        FoodIngredients("ŞEHRİYELİ PİRİNÇ PİLAVI", "380 Kalori", 1, 0),
        FoodIngredients("MEYVE", "120 Kalori", 1, 0),
        FoodIngredients("AYRAN", "70 Kalori", 1, 0),
        FoodIngredients("SEBZELİ ET SOTE", "449 Kalori", 2, 0),
        FoodIngredients("MERCİMEKLİ BULGUR PİLAVI", "338 Kalori", 2, 0),
        FoodIngredients("YOĞURT", "136 Kalori", 2, 0),
        FoodIngredients("YEŞİL SALATA", "45 Kalori", 2, 0),
        FoodIngredients("ETLİ KONSERVE TÜRLÜ", "318 Kalori", 3, 0),
        FoodIngredients("SOSLU MAKARNA", "328 Kalori", 3, 0),
        FoodIngredients("YOĞURTLU BUĞDAY SALATASI", "125 Kalori", 3, 0),
        FoodIngredients("MEYVE", "120 Kalori", 3, 0)
    )
    val htmlOfStudentFoodList = listOf(
        Food(1,htmlOfDateResult[0], htmlOfTotalCaloriesResult[0], FoodListType.STUDENT),
        Food(2,htmlOfDateResult[1], htmlOfTotalCaloriesResult[1], FoodListType.STUDENT),
        Food(3,htmlOfDateResult[2], htmlOfTotalCaloriesResult[2], FoodListType.STUDENT)
    )

    const val htmlOfFoodListBlock = "<div class=\"yemekListesiBlok\">\n" +
            "  <div class=\"d-title\">Personel Menüsü</div>\n" +
            "  <div class=\"row clearfix\">\n" +
            "    <ul class=\"component--job-items mt-2\" style=\"width: 95%;\">\n" +
            "      <li class=\"menu\">\n" +
            "        <a href=\"javascript:;\">\n" +
            "          <span class=\"detail\">\n" +
            "            <span class=\"title\">28 Haziran 2021 Pazartesi <span style=\"display: inline-block; font-size: 12px; font-weight: normal;\">Öğlen</span></span>\n" +
            "            <span class=\"position\">\n" +
            "              <ul>\n" +
            "                <li style=\"list-style: circle;\">ETLİ KURU FASULYE <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">354 Kalori</span></li>\n" +
            "                <li style=\"list-style: circle;\">ŞEHRİYELİ PİRİNÇ PİLAVI <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">380 Kalori</span></li>\n" +
            "                <li style=\"list-style: circle;\">MEYVE <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">120 Kalori</span></li>\n" +
            "                <li style=\"list-style: circle;\">AYRAN <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">70 Kalori</span></li>\n" +
            "              </ul>\n" +
            "            </span>\n" +
            "          </span>\n" +
            "          <span class=\"location hidden-xs\">\n" +
            "            <img src=\"/img/icon-location.svg\" alt=\"\" />\n" +
            "            924\n" +
            "            <span style=\"display: inline-block; font-size: 9px; font-weight: normal;\"> Kalori</span>\n" +
            "          </span>\n" +
            "        </a>\n" +
            "      </li>\n" +
            "      <li class=\"menu\">\n" +
            "        <a href=\"javascript:;\">\n" +
            "          <span class=\"detail\">\n" +
            "            <span class=\"title\">29 Haziran 2021 Salı <span style=\"display: inline-block; font-size: 12px; font-weight: normal;\">Öğlen</span></span>\n" +
            "            <span class=\"position\">\n" +
            "              <ul>\n" +
            "                <li style=\"list-style: circle;\">SEBZELİ ET SOTE <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">449 Kalori</span></li>\n" +
            "                <li style=\"list-style: circle;\">MERCİMEKLİ BULGUR PİLAVI <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">338 Kalori</span></li>\n" +
            "                <li style=\"list-style: circle;\">YOĞURT <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">136 Kalori</span></li>\n" +
            "                <li style=\"list-style: circle;\">YEŞİL SALATA <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">45 Kalori</span></li>\n" +
            "              </ul>\n" +
            "            </span>\n" +
            "          </span>\n" +
            "          <span class=\"location hidden-xs\">\n" +
            "            <img src=\"/img/icon-location.svg\" alt=\"\" />\n" +
            "            968\n" +
            "            <span style=\"display: inline-block; font-size: 9px; font-weight: normal;\"> Kalori</span>\n" +
            "          </span>\n" +
            "        </a>\n" +
            "      </li>\n" +
            "      <li class=\"menu\">\n" +
            "        <a href=\"javascript:;\">\n" +
            "          <span class=\"detail\">\n" +
            "            <span class=\"title\">30 Haziran 2021 Çarşamba <span style=\"display: inline-block; font-size: 12px; font-weight: normal;\">Öğlen</span></span>\n" +
            "            <span class=\"position\">\n" +
            "              <ul>\n" +
            "                <li style=\"list-style: circle;\">ETLİ KONSERVE TÜRLÜ <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">318 Kalori</span></li>\n" +
            "                <li style=\"list-style: circle;\">SOSLU MAKARNA <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">328 Kalori</span></li>\n" +
            "                <li style=\"list-style: circle;\">YOĞURTLU BUĞDAY SALATASI <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">125 Kalori</span></li>\n" +
            "                <li style=\"list-style: circle;\">MEYVE <span style=\"display: inline-block; font-size: 10px; font-weight: normal;\">120 Kalori</span></li>\n" +
            "              </ul>\n" +
            "            </span>\n" +
            "          </span>\n" +
            "          <span class=\"location hidden-xs\">\n" +
            "            <img src=\"/img/icon-location.svg\" alt=\"\" />\n" +
            "            891\n" +
            "            <span style=\"display: inline-block; font-size: 9px; font-weight: normal;\"> Kalori</span>\n" +
            "          </span>\n" +
            "        </a>\n" +
            "      </li>\n" +
            "    </ul>\n" +
            "  </div>\n" +
            "</div>\n"
}