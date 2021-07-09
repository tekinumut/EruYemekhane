package com.tekinumut.eruyemekhane.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MainApiService {

    @GET("{foodListType}")
    suspend fun getFoodList(@Path("foodListType") foodListType: String): Response<String>
}