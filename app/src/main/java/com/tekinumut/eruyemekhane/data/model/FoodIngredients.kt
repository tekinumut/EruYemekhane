package com.tekinumut.eruyemekhane.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Food::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("foodCreatorId"),
        onDelete = ForeignKey.CASCADE
    )], indices = [Index(value = ["foodCreatorId"])]
)
data class FoodIngredients(
    // SEBZELİ BULGUR PİLAVI
    val name: String?,
    // 227 Kalori
    val calorie: String?,
    // Id which belongs to Food
    val foodCreatorId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
