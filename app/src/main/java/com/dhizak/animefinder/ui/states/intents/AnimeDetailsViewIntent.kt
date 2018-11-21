package com.dhizak.animefinder.ui.states.intents

import com.dhizak.animefinder.model.Anime

sealed class AnimeDetailsViewIntent : BaseViewIntent() {
    class Init(val animeId : Int) : AnimeDetailsViewIntent()
}