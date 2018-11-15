package com.dhizak.animefinder.model.api.retrofit

import com.dhizak.animefinder.model.Anime
import com.dhizak.animefinder.model.api.envelope.SearchResultEnvelope
import com.dhizak.animefinder.model.api.envelope.TopItemsEnvelope
import com.dhizak.animefinder.model.constants.SearchType
import com.dhizak.animefinder.model.constants.TopSubtypes
import com.dhizak.animefinder.model.constants.TopType
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyAnimeListInterface {

    @GET("/top/{type}/{page}/{subtypes}")
    fun getAnimeList(@Path("type") type: TopType, @Path("page") page: Int, @Path("subtypes") subtypes: TopSubtypes): Deferred<Response<TopItemsEnvelope>>

    @GET("/top/{type}/{page}")
    fun getAnimeList(@Path("type") type: TopType, @Path("page") page: Int): Deferred<Response<TopItemsEnvelope>>

    @GET("anime/{id}")
    fun getAnime(@Path("id") id: Int): Deferred<Response<Anime>>

    @GET("/search/{type}")
    fun searchAnime(@Path("type") type: SearchType, @Query("q") query: String, @Query("page") page: Int):  Deferred<Response<SearchResultEnvelope>>
}