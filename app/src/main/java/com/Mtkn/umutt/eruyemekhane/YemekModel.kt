package com.Mtkn.umutt.eruyemekhane

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "YemekList")
class YemekModel(
    @PrimaryKey(autoGenerate = true)
    var ty_id: Int? = null,
    var tarih: String = "",
    var yemekler: String = "",
    var type: Int = 0
)