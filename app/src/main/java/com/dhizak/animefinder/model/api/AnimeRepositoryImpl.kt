package com.dhizak.animefinder.model.api

import com.dhizak.animefinder.model.Anime
import com.dhizak.animefinder.model.api.envelope.SearchResultEnvelope
import com.dhizak.animefinder.model.api.envelope.TopItemsEnvelope
import com.dhizak.animefinder.model.api.retrofit.MyAnimeListInterface
import com.dhizak.animefinder.model.constants.SearchType
import com.dhizak.animefinder.model.constants.TopSubtypes
import com.dhizak.animefinder.model.constants.TopType
import com.dhizak.animefinder.model.repository.AnimeRepository
import io.reactivex.Observable

class AnimeRepositoryImpl(val animeListRetrofit: MyAnimeListInterface) : AnimeRepository {

    override fun getAnime(animeId: Int): Observable<Anime> {
        return animeListRetrofit.getAnime(animeId)
    }

    override fun searchAnime(query: String, page: Int): Observable<SearchResultEnvelope> {
        return animeListRetrofit.searchAnime(SearchType.anime,query = query,page = page)
    }

    override fun getTopAnime(page: Int): Observable<TopItemsEnvelope> {
        return animeListRetrofit.getAnimeList(type = TopType.anime, page = page)
    }

    override fun getTopManga(page: Int): Observable<TopItemsEnvelope> {
        return animeListRetrofit.getAnimeList(type = TopType.manga, page = page)
    }

    override fun getTopItems(topType: TopType, page: Int, subtypes: TopSubtypes): Observable<TopItemsEnvelope> {
        return animeListRetrofit.getAnimeList(type = TopType.anime, page = page, subtypes = subtypes)

    }

}