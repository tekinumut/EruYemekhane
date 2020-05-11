package com.Mtkn.umutt.eruyemekhane.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.Mtkn.umutt.eruyemekhane.YemekModel
import com.Mtkn.umutt.eruyemekhane.library.Constants

@Dao
interface YemekDAO {

    /**
     * İlgili gruba ait yemek listesini getirir.
     * Akşam menülerini gizler.
     */
    @Query("Select * from YemekList where type = :type and tarih NOT LIKE '%' || 'Akşam' || '%'")
    fun getFoods(type: Int): LiveData<List<YemekModel>>

    /**
     * Mevcut tüm yemek bilgilerini veritabanına ekler
     */
    @Insert
    suspend fun addFoods(yemekModel: List<YemekModel>)

    /**
     * Tüm yemek verisini siler
     */
    @Query("Delete from YemekList")
    suspend fun deleteAllFoods()

    /**
     * İlgili gruptaki yemekleri siler
     */
    @Query("Delete from YemekList where type =:type")
    suspend fun deleteFoodOfType(type: Int)

//    /**
//     * type değerini 1 ise 0 -- 0 ise 1 yapar.
//     * Ayarlar menüsünde firstTab değeri değişince mevcut veriler güncellenmeli
//     * */
//    @Query("UPDATE YemekList SET type = CASE type WHEN 0 THEN 1 WHEN 1 THEN 0 ELSE type END")
//    suspend fun inversionTypeValues()

    /**
     * Veritabanına yemek listesini eklemeden önce tüm veriler silinir.
     * Transaction birden çok veritabanı işlemini tek protokolde gerçekleştirir.
     * @param type -1 için tüm veriler silinip güncellenir.
     *              0 ve 1 için ilgili liste güncellenir.
     */
    @Transaction
    suspend fun updateList(yemekModel: List<YemekModel>, type: Int = Constants.bothType) {
        if (type == Constants.bothType) deleteAllFoods() else deleteFoodOfType(type)
        addFoods(yemekModel)
    }
}