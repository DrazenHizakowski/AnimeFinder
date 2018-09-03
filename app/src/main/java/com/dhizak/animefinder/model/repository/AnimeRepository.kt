package com.dhizak.animefinder.model.repository

import com.dhizak.animefinder.model.Anime
import com.dhizak.animefinder.model.api.envelope.SearchResultEnvelope
import com.dhizak.animefinder.model.api.envelope.TopItemsEnvelope
import com.dhizak.animefinder.model.constants.TopSubtypes
import com.dhizak.animefinder.model.constants.TopType
import io.reactivex.Observable

interface AnimeRepository{

    fun getTopAnime(page : Int) : Observable<TopItemsEnvelope>

    fun getTopManga(page : Int) : Observable<TopItemsEnvelope>

    fun getTopItems(topType : TopType, page: Int, subtypes: TopSubtypes) : Observable<TopItemsEnvelope>

    fun getAnime(animeId : Int) : Observable<Anime>

    fun searchAnime(query : String,page : Int) : Observable<SearchResultEnvelope>

}