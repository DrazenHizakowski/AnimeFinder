package com.dhizak.animefinder.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.dhizak.animefinder.AnimeFinderApplication
import com.dhizak.animefinder.R
import com.dhizak.animefinder.model.api.AnimeRepositoryImpl
import com.dhizak.animefinder.model.repository.AnimeRepository
import com.dhizak.animefinder.ui.controllers.TopListController
import com.facebook.drawee.backends.pipeline.Fresco
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var router : Router
    private lateinit var animeItemsRepository : AnimeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)
        router = Conductor.attachRouter(this,changeHandler,savedInstanceState)

        if(!router.hasRootController()){
            router.setRoot(RouterTransaction.with(TopListController()))
        }
        animeItemsRepository = AnimeRepositoryImpl(AnimeFinderApplication.myAnimeList)
        animeItemsRepository.getTopAnime(1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    fun changeController(controller: Controller){
        router.pushController(RouterTransaction.with(controller))
    }

    override fun onBackPressed() {
        if(!router.handleBack()){
            super.onBackPressed()
        }
//        router.popCurrentController()
    }
}
