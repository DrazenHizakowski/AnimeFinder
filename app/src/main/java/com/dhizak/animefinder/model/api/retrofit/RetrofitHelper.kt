package com.dhizak.animefinder.model.api.retrofit

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        return retrofit.create(MyAnimeListInterface::class.java)
    }

}