package com.dhizak.animefinder.ui.viewmodel

import com.dhizak.animefinder.R
import com.dhizak.animefinder.model.repository.AnimeRepository
import com.dhizak.animefinder.ui.states.intents.AnimeDetailsViewIntent
import com.dhizak.animefinder.ui.states.states.AnimeDetailsViewState
import com.dhizak.animefinder.ui.states.states.BaseViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by drazen on 26.09.18.
 */
class AnimeDetailsViewModel : BaseViewModel<AnimeDetailsViewIntent, AnimeDetailsViewState>(), CoroutineScope,KoinComponent {

    val animeRepository : AnimeRepository by inject()

    private val parentJob = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.Main + parentJob

    override fun gotFromView(viewAction: AnimeDetailsViewIntent) {
        when (viewAction) {
            is AnimeDetailsViewIntent.Init -> {
                getAnime(viewAction.animeId)
            }
        }
    }


    fun getAnime(animeId: Int) = launch {
        val call = animeRepository.getAnime(animeId)
        val response = call.await()
        if(response.isSuccessful){
            postToView(AnimeDetailsViewState.ShowAnimeDetails(response.body()!!))
        }else{
            val error =  BaseViewState.ShowError(errorResource = R.string.failed_to_get_anime)
            error as AnimeDetailsViewState
            postToView(error)
        }
    }

}