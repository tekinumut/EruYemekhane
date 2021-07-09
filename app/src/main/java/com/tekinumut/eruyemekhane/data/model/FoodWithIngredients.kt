package com.tekinumut.eruyemekhane.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class FoodWithIngredients(
    @Embedded var food: Food,
    @Relation(
        parentColumn = "id",
        entityColumn = "foodCreatorId"
    )
    val foodIngredients: List<FoodIngredients>
)