package com.example.adview.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {
    const val BASE_URL ="https://gist.githubusercontent.com/"
    val key = "3lvis/3799feea005ed49942dcb56386ecec2b/raw/63249144485884d279d55f4f3907e37098f55c74/discover.json"

    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(makeOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(RetrofitService::class.java)
    }

    fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
            HttpLoggingInterceptor.Level.BODY
        return logging
    }
}