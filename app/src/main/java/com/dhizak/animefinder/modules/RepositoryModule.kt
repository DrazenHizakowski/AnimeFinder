package com.dhizak.animefinder.modules

import com.dhizak.animefinder.model.api.AnimeRepositoryImpl
import com.dhizak.animefinder.model.api.YoutubeThumbFetcher
import com.dhizak.animefinder.model.api.retrofit.MyAnimeListInterface
import com.dhizak.animefinder.model.api.retrofit.adapters.DateAdapter
import com.dhizak.animefinder.model.repository.AnimeRepository
import com.dhizak.animefinder.modules.RemoteRepository.BASE_URL
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import org.koin.dsl.module.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object RemoteRepository {
    const val BASE_URL = "BASE_URL"
}

fun createBaseUrl() = "https://api.jikan.moe/"

inline fun <reified T> createWebService(baseUrl: String, okHttpClient: OkHttpClient, converterFactory: Converter.Factory): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    return retrofit.create(T::class.java)
}

fun createOkHttp(): OkHttpClient {
    val logInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder()
            .addInterceptor(StethoInterceptor())
            .addNetworkInterceptor(logInterceptor).build()
}

fun createConverterFactory(): Converter.Factory {
    val moshi = Moshi.Builder()
    moshi.add(DateAdapter())
    return MoshiConverterFactory.create(moshi.build())
}

val repositoryModule = module {
    single(BASE_URL) { createBaseUrl() }
    single { createOkHttp() }
    single { createConverterFactory() }
    single { createWebService<MyAnimeListInterface>(get(BASE_URL), get(),get()) }
    single { YoutubeThumbFetcher(get()) }
    single<AnimeRepository> { AnimeRepositoryImpl(get()) }
}