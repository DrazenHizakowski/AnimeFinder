package com.dhizak.animefinder.ui.states.intents

sealed class AnimeListViewIntents : BaseViewIntent() {
    class GetAnime(val query : String,val offset : Int) : AnimeListViewIntents()
}