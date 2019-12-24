package com.Mtkn.umutt.eruyemekhane.room

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.Mtkn.umutt.eruyemekhane.YemekModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun getFoods(type: Int, context: Context): LiveData<Result<List<YemekModel>>> = liveData {
        try {
            emit(Result.success(YemekDatabase.getInstance(context).yemekDao().getFoods(type)))
        } catch (e: Exception) {
            emit(Result.failure(e))
            Log.e("umutt", "error: " + e.message)
        }
    }

    fun updateFoods(type: Int, yemekModel: List<YemekModel>, context: Context) = CoroutineScope(IO).launch {
        try {
            YemekDatabase.getInstance(context).yemekDao().updateList(type, yemekModel)
        } catch (e: Exception) {
            Log.e("updateFoods", "error: " + e.message)
        }
    }

}

