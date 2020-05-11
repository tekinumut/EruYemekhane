package com.Mtkn.umutt.eruyemekhane

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.Mtkn.umutt.eruyemekhane.library.Constants

@Entity(tableName = "YemekList")
class YemekModel(
    @PrimaryKey(autoGenerate = true)
    /**
     * Zorunlu olarak kullanılan yemek id'si
     */
    val yemek_id: Int? = null,
    /**
     * Yemeğin verileceği tarih
     */
    val tarih: String,
    /**
     * İlgili güne ait düzenlenmiş yemek içeriği
     */
    val yemekler: String,
    /**
     * Yemek listesinin ait olduğu grup
     * 0 -> Öğrenci
     * 1 -> Personel
     */
    val type: Int = Constants.ogrType
)