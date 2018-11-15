package com.dhizak.animefinder.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.dhizak.animefinder.AnimeFinderApplication
import com.dhizak.animefinder.model.Anime
import com.dhizak.animefinder.model.api.AnimeRepositoryImpl
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Created by drazen on 26.09.18.
 */
class AnimeDetailsViewModel : ViewModel(),CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    lateinit var liveData : MutableLiveData<Anime>
    var animeRepository = AnimeRepositoryImpl(AnimeFinderApplication.myAnimeList)
    /*
    public fun getAnime(animeId : Int) : LiveData<Anime>{
        animeRepository.animeListRetrofit.getAnime(animeId)
                    liveData.value = it
                    liveData.postValue(it)
                })
        return liveData
    }
    */
}