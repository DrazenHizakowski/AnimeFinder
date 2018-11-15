package com.dhizak.animefinder.ui.controllers

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.dhizak.animefinder.R
import com.dhizak.animefinder.R.id.anime_image
import com.dhizak.animefinder.model.Anime
import com.dhizak.animefinder.ui.viewmodel.AnimeDetailsViewModel
import com.dhizak.animefinder.util.image.ImageLoader
import com.google.android.youtube.player.YouTubeBaseActivity
import kotlinx.android.synthetic.main.fragment_anime_description.*

/**
 * Created by drazen on 26.09.18.
 */
class AnimeDetailsController : FragmentActivity() {

    companion object {
        val ANIME_ID = "anime_id"
    }

    lateinit var animeDetailsViewModel : AnimeDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_anime_description)
        animeDetailsViewModel = ViewModelProviders.of(this).get(AnimeDetailsViewModel::class.java)
        if (intent.hasExtra(ANIME_ID)) {
          //  animeDetailsViewModel.getAnime(intent.getIntExtra(ANIME_ID, -1)).observe(this,
            ///        Observer<Anime> { t -> setAnimeView(t) })
        }else{
            finish()
        }
    }

    private fun setAnimeView(anime : Anime?){
        if(anime==null) return

        ImageLoader(fitCenter = true,imageHolder = anime_image,imagePath = anime.image_url).loadImage()

    }

}