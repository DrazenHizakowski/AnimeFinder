package com.dhizak.animefinder.model.repository

import com.dhizak.animefinder.model.Anime
import com.dhizak.animefinder.model.api.envelope.SearchResultEnvelope
import com.dhizak.animefinder.model.api.envelope.TopItemsEnvelope
import com.dhizak.animefinder.model.constants.TopSubtypes
import com.dhizak.animefinder.model.constants.TopType
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface AnimeRepository{

    fun getTopAnime(page : Int) : Deferred<Response<TopItemsEnvelope>>

    fun getTopManga(page : Int) : Deferred<Response<TopItemsEnvelope>>

    fun getTopItems(topType : TopType, page: Int, subtypes: TopSubtypes) : Deferred<Response<TopItemsEnvelope>>

    fun getAnime(animeId : Int) : Deferred<Response<Anime>>

    fun searchAnime(query : String,page : Int) : Deferred<Response<SearchResultEnvelope>>

}