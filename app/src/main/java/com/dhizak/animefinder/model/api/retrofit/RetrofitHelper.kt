package com.dhizak.animefinder.model.api.retrofit

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitHelper {

    fun create(): MyAnimeListInterface {
        val logInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(StethoInterceptor())
                .addNetworkInterceptor(logInterceptor)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        return retrofit.create(MyAnimeListInterface::class.java)
    }

}