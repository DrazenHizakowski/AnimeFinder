package com.dhizak.animefinder

import android.app.Application
import com.dhizak.animefinder.model.api.retrofit.MyAnimeListInterface
import com.dhizak.animefinder.model.api.retrofit.RetrofitHelper
import com.facebook.stetho.Stetho

class AnimeFinderApplication : Application() {

    companion object {
        lateinit var myAnimeList : MyAnimeListInterface
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        myAnimeList = RetrofitHelper.create()
    }

}