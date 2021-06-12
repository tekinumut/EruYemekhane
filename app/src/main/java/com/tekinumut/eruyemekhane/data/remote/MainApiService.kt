package com.tekinumut.eruyemekhane.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface MainApiService {

    @GET("ogrenci-yemek-listesi")
    suspend fun getStudentFoodList(): Response<String>

    @GET("personel-yemek-listesi")
    suspend fun getPersonalFoodList(): Response<String>

}