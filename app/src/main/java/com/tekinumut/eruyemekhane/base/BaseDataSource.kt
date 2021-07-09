package com.tekinumut.eruyemekhane.base

import com.tekinumut.eruyemekhane.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {

    // All api requests go through here
    protected suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.Success(body)
            }
            return Resource.Error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Resource.Error(e.message ?: e.toString())
        }
    }

//    protected fun <T> observeApi(apiCall: suspend () -> Response<T>): LiveData<Resource<T>> =
//        liveData {
//            emit(Resource.Loading)
//            emit(safeApiCall { apiCall.invoke() })
//        }

//    protected fun <T> observeDatabase(databaseQuery: () -> LiveData<T>): LiveData<Resource<T>> =
//        liveData {
//            emit(Resource.Loading)
//            val source: LiveData<Resource<T>> = databaseQuery.invoke().map { Resource.Success(it) }
//            emitSource(source)
//        }

}