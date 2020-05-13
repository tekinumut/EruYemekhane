package com.Mtkn.umutt.eruyemekhane

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.Mtkn.umutt.eruyemekhane.library.Constants
import com.Mtkn.umutt.eruyemekhane.library.Resource
import com.Mtkn.umutt.eruyemekhane.library.Utility
import com.Mtkn.umutt.eruyemekhane.room.YemekDAO
import com.Mtkn.umutt.eruyemekhane.room.YemekDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // ROOM veritabanı üzerinde işlem yapmamızı sağlayan SQL sorgularına erişir
    private val yemekDao: YemekDAO = YemekDatabase.getInstance(application).yemekDao()

    /**
     * erciyes.edu.tr'den yemek listelerini çeker.
     * emit bağlantının o anki durumunu livedata'ya değer olarak verir.
     */
    fun getFoodData(type: Int): LiveData<Resource<List<YemekModel>>> = liveData(Dispatchers.IO) {
        emit(Resource.InProgress)
        try {
            // erciyes.edu.tr'den sayfa kaynağını al
            val doc = Jsoup.connect(Constants.URL).timeout(5000).get()
            // val doc = Jsoup.parse(Constants.fullList)
            // Alınan sayfa kaynağını düzenlenmiş veriye dönüştür.
            val specificList = Utility.getSpecificList(doc, type)
            // Bu veriyi veritabanına kayıt et
            updateFoods(specificList, type)
            emit(Resource.Success(specificList))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    /**
     * Veritabanından belirtilen listeyi çeker.
     */
    fun getFoods(type: Int): LiveData<List<YemekModel>> = yemekDao.getFoods(type)

    /**
     * Veritabanına güncel listeleri ekler.
     */
    private fun updateFoods(yemekModel: List<YemekModel>, type: Int) = viewModelScope.launch(Dispatchers.IO) {
        yemekDao.updateList(yemekModel, type)
    }

}