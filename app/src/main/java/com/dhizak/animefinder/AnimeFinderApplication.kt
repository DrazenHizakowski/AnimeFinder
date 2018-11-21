package com.dhizak.animefinder

import android.app.Application
import com.dhizak.animefinder.modules.repositoryModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin

class AnimeFinderApplication : Application() {

    companion object {
        lateinit var INSTANCE : AnimeFinderApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        INSTANCE = this
        startKoin(this, listOf(repositoryModule))
    }

}