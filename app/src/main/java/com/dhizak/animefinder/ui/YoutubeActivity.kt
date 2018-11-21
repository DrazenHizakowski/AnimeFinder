package com.dhizak.animefinder.ui

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.dhizak.animefinder.BuildConfig
import com.dhizak.animefinder.R
import com.dhizak.animefinder.model.api.YoutubeThumbFetcher
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.youtube_activity_layout.*
import org.koin.android.ext.android.inject
import org.koin.standalone.KoinComponent

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, KoinComponent {

    lateinit var youtubeVideo: String
    val youtubeThumbFetcher : YoutubeThumbFetcher by inject()

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
        Toast.makeText(this, "Failed to initialize youtube player", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
        p1?.loadVideo(youtubeThumbFetcher.getVideoId(youtubeVideo))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.youtube_activity_layout)
        if (intent.hasExtra(YOUTUBE_VIDEO_EXTRA)) {
            youtubeVideo = intent.getStringExtra(YOUTUBE_VIDEO_EXTRA)
        } else {
            finish()
            return
        }
        youtubePlayerView.initialize(BuildConfig.GOOGLE_API_KEY, this)
        backButton.setOnClickListener{
            finish()
        }
    }
}
val YOUTUBE_VIDEO_EXTRA = "youtube_video_extra"
