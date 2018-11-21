package com.dhizak.animefinder.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.dhizak.animefinder.model.Top
import com.dhizak.animefinder.model.repository.AnimeRepository
import com.dhizak.animefinder.ui.lists.adapters.AnimeListAdapter
import com.dhizak.animefinder.ui.states.intents.AnimeListViewIntents
import com.dhizak.animefinder.ui.states.states.AnimeListViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.coroutines.CoroutineContext


class AnimeListViewModel : BaseViewModel<AnimeListViewIntents, AnimeListViewState>(), CoroutineScope, KoinComponent {

    val TAG = "AnimeListViewModel"

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    val PAGE_SIZE = 50
    private val animeRepo: AnimeRepository  by inject()
    private var lastQuery = ""

    override fun gotFromView(viewAction: AnimeListViewIntents): Unit = when (viewAction) {
        is AnimeListViewIntents.GetAnime -> {
            if (viewAction.query.isEmpty()) {
                getTopAnime((viewAction.offset / PAGE_SIZE)+1)
            }else{
                searchAnimeAsync(viewAction.query,(viewAction.offset / PAGE_SIZE)+1)
            }
        }
    }

    private fun searchAnimeAsync(query: String, i: Int) {
        searchAnime(query,i)
    }

    fun getTopAnime(page: Int) {
        fetchAnime(page)
    }

    fun fetchAnime(page: Int) = launch {
        val deferred = animeRepo.getTopAnime(page)
        val result = deferred.await()
        if (result.isSuccessful) {
            if (page == 0) {
                postToView(AnimeListViewState.InsertNewAnime(lastQuery, result.body()!!.top))
            } else {
                postToView(AnimeListViewState.UpdateAnimeList(lastQuery, result.body()!!.top))
            }
        } else {
            //error handling
        }
    }

    fun searchAnime(query: String, page: Int) = launch {
        val deferred = animeRepo.searchAnime(query, page)
        val result = deferred.await()
        if (result.isSuccessful) {
            if (page == 0) {
                postToView(AnimeListViewState.InsertNewAnime(lastQuery, result.body()!!.result))
            } else {
                postToView(AnimeListViewState.UpdateAnimeList(lastQuery, result.body()!!.result))
            }
        } else {

        }
        lastQuery = query
    }
}
