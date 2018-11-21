package com.dhizak.animefinder.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.dhizak.animefinder.R
import com.dhizak.animefinder.ui.base.BaseMVVMFragment
import com.dhizak.animefinder.ui.callbacks.OnAnimeSelectedListener
import com.dhizak.animefinder.ui.lists.adapters.AnimeListAdapter
import com.dhizak.animefinder.ui.lists.listeners.OnAnimeListListener
import com.dhizak.animefinder.ui.states.intents.AnimeListViewIntents
import com.dhizak.animefinder.ui.states.states.AnimeListViewState
import com.dhizak.animefinder.ui.states.states.BaseViewState
import com.dhizak.animefinder.ui.viewmodel.AnimeListViewModel
import com.dhizak.animefinder.ui.viewmodel.BaseViewModel
import com.dhizak.animefinder.ui.widgets.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.top_anime_list.*
import kotlin.properties.Delegates


class TopListFragment : BaseMVVMFragment<AnimeListViewState, AnimeListViewIntents>(), OnAnimeListListener {

    val TAG = "TopListController"
    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener
    val adapter = AnimeListAdapter(this)
    var searchMode = false

    override fun getViewModel(): Class<BaseViewModel<AnimeListViewIntents, AnimeListViewState>> {
        return AnimeListViewModel::class.java as Class<BaseViewModel<AnimeListViewIntents, AnimeListViewState>>
    }

    override fun render(viewState: AnimeListViewState) = when (viewState) {
        is AnimeListViewState.InsertNewAnime -> {
            adapter.insertNewItems(viewState.animes)
        }
        is AnimeListViewState.UpdateAnimeList -> {
            adapter.addItems(viewState.animes)
        }
    }

    override fun onAnimeSelected(animeId: Int) {
        val animeListener = activity as OnAnimeSelectedListener
        animeListener.OnAnimeSelected(animeId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.top_anime_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setClickListeners()
        postToModel(AnimeListViewIntents.GetAnime("", 0))
    }

    private fun setAdapter() {
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                postToModel(AnimeListViewIntents.GetAnime(searchField.text.toString(), adapter.itemCount))
            }
        }
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener)
    }


    private fun setClickListeners() {
        clearButton.setOnClickListener {
            searchField.setText("")
            postToModel(AnimeListViewIntents.GetAnime(searchField.text.toString(), 0))
        }
        filterButton.setOnClickListener {}

        searchButton.setOnClickListener {
            if (searchField.text.toString().length > 3) {
                endlessRecyclerViewScrollListener.resetState()
                postToModel(AnimeListViewIntents.GetAnime(searchField.text.toString(), 0))
                searchMode = true
            } else {
                searchMode = false
                Toast.makeText(activity, "3 or more letters!", Toast.LENGTH_SHORT).show()
            }
        }
        searchField.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                postToModel(AnimeListViewIntents.GetAnime(searchField.text.toString(), 0))
                return@OnEditorActionListener true
            }
            false
        })
    }
}
