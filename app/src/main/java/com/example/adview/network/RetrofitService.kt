package com.example.adview.network

import com.example.adview.model.AdResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    @GET("3lvis/3799feea005ed49942dcb56386ecec2b/raw/63249144485884d279d55f4f3907e37098f55c74/discover.json")

    fun getAds(): Deferred<Response<AdResponse>>
}