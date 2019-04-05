package com.example.adview.network

import com.example.adview.model.Ad
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @GET("/ad")
    fun getAds(): Deferred<Response<List<Ad>>>
}