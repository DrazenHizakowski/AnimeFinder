package com.dhizak.animefinder.model.api

import com.dhizak.animefinder.model.Anime
import com.dhizak.animefinder.model.api.envelope.SearchResultEnvelope
import com.dhizak.animefinder.model.api.envelope.TopItemsEnvelope
import com.dhizak.animefinder.model.api.retrofit.MyAnimeListInterface
import com.dhizak.animefinder.model.constants.SearchType
import com.dhizak.animefinder.model.constants.TopSubtypes
import com.dhizak.animefinder.model.constants.TopType
import com.dhizak.animefinder.model.repository.AnimeRepository
import kotlinx.coroutines.Deferred
import retrofit2.Response

class AnimeRepositoryImpl(val animeListRetrofit: MyAnimeListInterface) : AnimeRepository {

    override fun getAnime(animeId: Int): Deferred<Response<Anime>> {
        return animeListRetrofit.getAnime(animeId)
    }

    override fun searchAnime(query: String, page: Int): Deferred<retrofit2.Response<SearchResultEnvelope>> {
        return animeListRetrofit.searchAnime(SearchType.anime,query = query,page = page)
    }

    override fun getTopAnime(page: Int): Deferred<Response<TopItemsEnvelope>> {
        return animeListRetrofit.getAnimeList(type = TopType.anime, page = page)
    }

    override fun getTopManga(page: Int): Deferred<Response<TopItemsEnvelope>> {
        return animeListRetrofit.getAnimeList(type = TopType.manga, page = page)
    }

    override fun getTopItems(topType: TopType, page: Int, subtypes: TopSubtypes): Deferred<Response<TopItemsEnvelope>> {
        return animeListRetrofit.getAnimeList(type = TopType.anime, page = page, subtypes = subtypes)

    }

}