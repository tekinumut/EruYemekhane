package com.tekinumut.eruyemekhane.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tekinumut.eruyemekhane.utils.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseDataSource {

    // All api requests go through here
    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) Resource.Error(NO_DATA)
                else Resource.Success(body)
            } else {
                Resource.Error(SERVER_ERROR)
            }
        } catch (e: Exception) {
            Log.e("ServiceRepository", e.message.toString())
            when (e) {
                is HttpException -> Resource.Error(SERVER_ERROR)
                is IOException -> Resource.Error(SERVER_ERROR)
                else -> Resource.Error(UNKNOWN_ERROR)
            }
        }
    }

    protected fun <T> observeApi(apiCall: suspend () -> Response<T>): LiveData<Resource<T>> =
        liveData {
            emit(Resource.Loading)
            emit(safeApiCall { apiCall.invoke() })
        }

    companion object {
        const val SERVER_ERROR = "Sunucuya bağlanırken bir hata meydana geldi."
        const val NO_DATA = "Gösterilecek veri bulunamadı."
        const val UNKNOWN_ERROR = "Bilinmeyen bir hata meydana geldi."
    }
}