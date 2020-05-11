package com.Mtkn.umutt.eruyemekhane.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.Mtkn.umutt.eruyemekhane.YemekModel

@Database(entities = [YemekModel::class], version = 1)
abstract class YemekDatabase : RoomDatabase() {

    abstract fun yemekDao(): YemekDAO

    companion object {
        @Volatile
        private var INSTANCE: YemekDatabase? = null

        fun getInstance(context: Context): YemekDatabase {
            val tempInstance = INSTANCE
            tempInstance?.let { return tempInstance }

            synchronized(YemekDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YemekDatabase::class.java,
                    "yemek_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}