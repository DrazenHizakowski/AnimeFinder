package com.dhizak.animefinder.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.dhizak.animefinder.AnimeFinderApplication
import com.dhizak.animefinder.model.Top
import com.dhizak.animefinder.model.api.AnimeRepositoryImpl
import com.dhizak.animefinder.model.repository.AnimeRepository
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class AnimeListViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    val TAG = "AnimeListViewModel"

    val PAGE_SIZE = 50

    private val animeRepo: AnimeRepository = AnimeRepositoryImpl(AnimeFinderApplication.myAnimeList)

    private var topAnime: MutableLiveData<MutableList<Top>> = MutableLiveData()

    private var searchResult: MutableLiveData<MutableList<Top>> = MutableLiveData()

    fun getTopAnime(page: Int): LiveData<MutableList<Top>> {
        if (topAnime.value == null) {
            topAnime.value = mutableListOf()
        }
        if (topAnime.value?.size == 0 && page == 1) {
            fetchAnime(page)
        } else if (topAnime.value?.size!!.div(PAGE_SIZE) >= page) {

        } else {
            fetchAnime(page)
        }
        return topAnime
    }

    fun fetchAnime(page: Int) = launch {
        if (topAnime.value == null) {
            topAnime.value = mutableListOf()
        }
        val result = animeRepo.getTopAnime(page)
        topAnime.value?.addAll(result.await().body()?.top!!)
        topAnime.postValue(topAnime.value)
    }

    fun searchAnime(query: String, page: Int) = launch {
        if (searchResult.value == null) {
            searchResult.value = mutableListOf()
        }
        val result = animeRepo.searchAnime(query, page)
        searchResult.value?.addAll(result.await()?.body()?.result!!)
        searchResult.postValue(searchResult.value)
    }

    fun getAnimeListData(): LiveData<MutableList<Top>> {
        return searchResult
    }
}
