package com.dhizak.animefinder.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.dhizak.animefinder.model.Top
import android.arch.lifecycle.LiveData
import android.util.Log
import com.dhizak.animefinder.AnimeFinderApplication
import com.dhizak.animefinder.model.api.AnimeRepositoryImpl
import com.dhizak.animefinder.model.repository.AnimeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AnimeListViewModel : ViewModel() {

    val TAG = "AnimeListViewModel"

    val PAGE_SIZE = 50

    private val animeRepo : AnimeRepository = AnimeRepositoryImpl(AnimeFinderApplication.myAnimeList)

    private var topAnime : MutableLiveData<MutableList<Top>> = MutableLiveData()

    private var searchResult : MutableLiveData<MutableList<Top>> = MutableLiveData()

    fun getTopAnime(page : Int): LiveData<MutableList<Top>> {
        if(topAnime.value == null){
            topAnime.value = mutableListOf()
        }
        if(topAnime.value?.size == 0 && page == 1){
            fetchAnime(page)
        }else if(topAnime.value?.size!!.div(PAGE_SIZE) >= page){
            Log.e(TAG,"wrong part")
        }else{
            fetchAnime(page)
        }
        return topAnime
    }

    fun fetchAnime(page : Int){
        if(topAnime.value==null){
            topAnime.value = mutableListOf()
        }
        animeRepo.getTopAnime(page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e(TAG,"GOT THIS FROM API : ${it.top.size}")
                    topAnime.value?.addAll(it.top)
                    topAnime.postValue(topAnime.value)
                }, {
                    Log.e(TAG,"GOT SOME SHITY ERROR ${it.localizedMessage}")
                    it.printStackTrace()
                })
    }

    fun searchAnime(query : String,page : Int) : LiveData<MutableList<Top>>{
        if(searchResult.value==null){
            searchResult.value = mutableListOf()
        }
        animeRepo.searchAnime(query, page).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({
                    searchResult.value?.addAll(it.result)
                    searchResult.postValue(searchResult.value)
                }, {
                    it.printStackTrace()
                })
        return searchResult
    }
}