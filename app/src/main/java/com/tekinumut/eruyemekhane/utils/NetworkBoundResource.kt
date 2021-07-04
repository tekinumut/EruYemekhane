package com.tekinumut.eruyemekhane.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map

inline fun <T, A> networkBoundResource(
    crossinline databaseQuery: () -> LiveData<T>,
    crossinline networkCall: suspend () -> Resource<A>,
    crossinline saveCallResult: suspend (A) -> Unit,
    crossinline shouldFetch: () -> Boolean
): LiveData<Resource<T>> =
    liveData {
        emit(Resource.Loading)
        if (shouldFetch()) {
            val responseStatus = networkCall.invoke()
            if (responseStatus is Resource.Success) {
                saveCallResult(responseStatus.data!!)
                emitSource(databaseQuery.invoke().map { Resource.Success(it) })
            } else if (responseStatus is Resource.Error) {
                emit(Resource.Error(responseStatus.errorMessage))
                emitSource(databaseQuery.invoke().map { Resource.Success(it) })
            }
        } else {
            emitSource(databaseQuery.invoke().map { Resource.Success(it) })
        }
    }

//inline fun <ResultType, RequestType> networkBoundResource(
//    crossinline query: () -> Flow<ResultType>,
//    crossinline fetch: suspend () -> Resource<RequestType>,
//    crossinline saveFetchResult: suspend (RequestType) -> Unit,
//    crossinline shouldFetch: (ResultType) -> Boolean = { true }
//) = flow {
//    val data = query().first()
//
//    val flow = if (shouldFetch(data)) {
//        emit(Resource.Loading)
//        try {
//            when (val responseStatus = fetch.invoke()) {
//                is Resource.Success -> {
//                    saveFetchResult(responseStatus.data)
//                    query().map { Resource.Success(it) }
//                }
//                is Resource.Error -> query().map { Resource.Error(responseStatus.errorMessage) }
//                else -> throw TypeCastException()
//            }
//        } catch (throwable: Throwable) {
//            query().map { Resource.Error(throwable.message ?: throwable.toString()) }
//        }
//    } else {
//        query().map { Resource.Success(it) }
//    }
//
//    emitAll(flow)
//}
