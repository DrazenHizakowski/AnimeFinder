package com.dhizak.animefinder.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.dhizak.animefinder.AnimeFinderApplication
import com.dhizak.animefinder.model.Anime
import com.dhizak.animefinder.model.api.AnimeRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by drazen on 26.09.18.
 */
class AnimeDetailsViewModel : ViewModel() {

    lateinit var liveData : MutableLiveData<Anime>
    var animeRepository = AnimeRepositoryImpl(AnimeFinderApplication.myAnimeList)

    public fun getAnime(animeId : Int) : LiveData<Anime>{
        animeRepository.animeListRetrofit.getAnime(animeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(Consumer {
                    liveData.value = it
                    liveData.postValue(it)
                })
        return liveData
    }
}