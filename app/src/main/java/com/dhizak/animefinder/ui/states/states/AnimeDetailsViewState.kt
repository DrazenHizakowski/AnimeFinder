package com.dhizak.animefinder.ui.states.states

import android.support.annotation.StringRes
import com.dhizak.animefinder.model.Anime

sealed class AnimeDetailsViewState : BaseViewState() {
    class ShowAnimeDetails(val anime: Anime) : AnimeDetailsViewState()
}