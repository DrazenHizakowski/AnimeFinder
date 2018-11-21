package com.dhizak.animefinder.ui.states.states

import com.dhizak.animefinder.model.Anime
import com.dhizak.animefinder.model.Top

sealed class AnimeListViewState : BaseViewState() {
    class UpdateAnimeList(val query : String, val animes : List<Top>) : AnimeListViewState()
    class InsertNewAnime(val query : String, val animes : List<Top>) : AnimeListViewState()
}