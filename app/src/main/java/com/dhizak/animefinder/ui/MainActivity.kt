package com.dhizak.animefinder.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dhizak.animefinder.R
import com.dhizak.animefinder.ui.callbacks.OnAnimeSelectedListener
import com.dhizak.animefinder.ui.callbacks.OnBackListener
import com.dhizak.animefinder.ui.fragments.AnimeDetailsFragment
import com.dhizak.animefinder.ui.fragments.TopListFragment

class MainActivity : AppCompatActivity(), OnAnimeSelectedListener, OnBackListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openList()
    }

    override fun OnAnimeSelected(id: Int) {
        if (id == -1) {
            return
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.changeHandler,AnimeDetailsFragment.createInstance(id))
        transaction.addToBackStack(AnimeDetailsFragment.TAG)
        transaction.commit()
    }

    override fun OnBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(AnimeDetailsFragment.TAG)
        if(fragment!=null && fragment.isVisible){
            openList()
        }
    }

    fun openList(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.changeHandler,TopListFragment())
        transaction.addToBackStack(AnimeDetailsFragment.TAG)
        transaction.commit()
    }

    override fun onBackPressed() {
        OnBackPressed()
    }
}
