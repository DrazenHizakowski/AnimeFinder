package com.dhizak.animefinder.ui.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dhizak.animefinder.R
import com.dhizak.animefinder.model.Anime
import com.dhizak.animefinder.model.api.YoutubeThumbFetcher
import com.dhizak.animefinder.ui.YOUTUBE_VIDEO_EXTRA
import com.dhizak.animefinder.ui.YoutubeActivity
import com.dhizak.animefinder.ui.base.BaseMVVMFragment
import com.dhizak.animefinder.ui.states.intents.AnimeDetailsViewIntent
import com.dhizak.animefinder.ui.states.states.AnimeDetailsViewState
import com.dhizak.animefinder.ui.viewmodel.AnimeDetailsViewModel
import com.dhizak.animefinder.ui.viewmodel.BaseViewModel
import com.dhizak.animefinder.util.image.ImageLoader
import com.google.android.youtube.player.internal.r
import kotlinx.android.synthetic.main.fragment_anime_description.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.KoinContext
import org.koin.standalone.KoinComponent
import kotlin.coroutines.CoroutineContext

/**
 * Created by drazen on 26.09.18.
 */
class AnimeDetailsFragment : BaseMVVMFragment<AnimeDetailsViewState, AnimeDetailsViewIntent>(), KoinComponent,CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val thumbFetcher: YoutubeThumbFetcher by inject()

    companion object {
        val ANIME_ID = "anime_id"
        val TAG = "AnimeDetailsFragment"

        fun createInstance(animeId: Int): AnimeDetailsFragment {
            val fragment = AnimeDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(AnimeDetailsFragment.ANIME_ID, animeId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getViewModel(): Class<BaseViewModel<AnimeDetailsViewIntent, AnimeDetailsViewState>> {
        return AnimeDetailsViewModel::class.java as Class<BaseViewModel<AnimeDetailsViewIntent, AnimeDetailsViewState>>
    }

    override fun render(viewState: AnimeDetailsViewState) = when (viewState) {
        is AnimeDetailsViewState.ShowAnimeDetails -> {
            showAnime(viewState.anime)
        }
    }

    private fun showAnime(anime: Anime) {
        Glide.with(this).load(anime.image_url).into(animeImage)
        setCover(anime)
        movieTitle.text = anime.title
        synopsis.text = anime.synopsis
        durationText.text = anime.duration
        premieredText.text = anime.premiered
        statusText.text = anime.status
        genresLayout.addGenres(anime.genre)
        watchOnYoutubeButton.setOnClickListener {
            val intent = Intent(context,YoutubeActivity::class.java)
            intent.putExtra(YOUTUBE_VIDEO_EXTRA,anime.trailer_url)
            startActivity(intent)
        }
    }

    private fun setCover(anime : Anime) = launch{
        val videoId = thumbFetcher.getVideoId(anime.trailer_url!!)
        if (videoId != null) {
            val result = thumbFetcher.getThumbWithOkHttp(videoId, 0)
            coverPhoto.setImageBitmap(result.await())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_anime_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animeId = arguments?.getInt(ANIME_ID, -1)
        postToModel(AnimeDetailsViewIntent.Init(animeId!!))
    }

    lateinit var animeDetailsViewModel: AnimeDetailsViewModel

    private fun setAnimeView(anime: Anime?) {
        if (anime == null) return

        ImageLoader(fitCenter = true, imageHolder = animeImage, imagePath = anime.image_url).loadImage()
    }

}
