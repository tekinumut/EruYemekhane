package com.tekinumut.eruyemekhane.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tekinumut.eruyemekhane.data.enums.FoodListType

@Entity
data class Food(
    //10 Haziran 2021 Perşembe
    val date: String,
    // 1356 Kalori
    val totalCalorie: String?,
    // Ogrenci or Personel
    val type: FoodListType,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
