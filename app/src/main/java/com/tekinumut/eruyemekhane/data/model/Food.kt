package com.tekinumut.eruyemekhane.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tekinumut.eruyemekhane.data.enums.FoodListType
import com.tekinumut.eruyemekhane.utils.Constants

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    // 10 Haziran 2021 Perşembe
    val date: String,
    // 1356 Kalori
    val totalCalories: String?,
    // Ogrenci or Personel
    val type: FoodListType
)
