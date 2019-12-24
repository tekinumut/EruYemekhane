package com.Mtkn.umutt.eruyemekhane.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.Mtkn.umutt.eruyemekhane.YemekModel

@Dao
interface YemekDAO {

    @Insert
    suspend fun addFoods(yemekModel: List<YemekModel>)

    @Query("Select * from YemekList where type = :type")
    suspend fun getFoods(type: Int): List<YemekModel>

    @Query("Delete from YemekList where type = :type")
    suspend fun deleteAllFoods(type: Int)

    @Transaction
    suspend fun updateList(type: Int, yemekModel: List<YemekModel>) {
        deleteAllFoods(type)
        addFoods(yemekModel)
    }
}