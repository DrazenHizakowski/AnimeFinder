package com.dhizak.animefinder.ui.controllers

import android.arch.lifecycle.Observer
import android.content.Intent
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
import com.dhizak.animefinder.model.Top
import com.dhizak.animefinder.ui.lists.adapters.AnimeListAdapter
import com.dhizak.animefinder.ui.lists.listeners.OnAnimeListListener
import com.dhizak.animefinder.ui.viewmodel.AnimeListViewModel
import com.dhizak.animefinder.ui.widgets.EndlessRecyclerViewScrollListener
import work.beltran.conductorviewmodel.ViewModelController



class TopListController : ViewModelController(), OnAnimeListListener {

    override fun onAnimeSelected(animeId: Int) {
        val intent = Intent(activity,AnimeDetailsController::class.java)
        intent.putExtra(AnimeDetailsController.ANIME_ID,animeId)
        startActivity(intent)
    }

    val TAG = "TopListController"

    lateinit var clearButton: AppCompatImageView
    lateinit var searchButton: AppCompatImageView
    lateinit var filterButton: AppCompatImageView
    lateinit var searchField: AppCompatEditText
    lateinit var recyclerView: RecyclerView
    private lateinit var endlessRecyclerViewScrollListener : EndlessRecyclerViewScrollListener
    val adapter = AnimeListAdapter(this)
    lateinit var animeViewModel: AnimeListViewModel
    var searchMode = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.top_anime_list, container, false)
        clearButton = view.findViewById(R.id.clearButton)
        searchButton = view.findViewById(R.id.searchButton)
        filterButton = view.findViewById(R.id.filterList)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchField = view.findViewById(R.id.searchField)
        animeViewModel = viewModelProvider().get(AnimeListViewModel::class.java)
        setAdapter()
        setClickListeners()

        animeViewModel.getTopAnime(1).observe(this, Observer<MutableList<Top>> {
            adapter.addItems(it)
        })
        return view
    }

    private fun setAdapter(){
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        endlessRecyclerViewScrollListener =  object : EndlessRecyclerViewScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if(searchMode){
                    animeViewModel.searchAnime(page = page+1,query = searchField.text.toString())
                }else{
                    animeViewModel.getTopAnime(page = page+1)
                }
            }
        }
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener)
    }


    private fun setClickListeners() {
        clearButton.setOnClickListener {
            searchField.setText("")
            animeViewModel.getTopAnime(1)
        }
        filterButton.setOnClickListener {}
        animeViewModel.getAnimeListData().observe( this, Observer {
            adapter.addItems(it)
        })
        searchButton.setOnClickListener {
            if (searchField.text.toString().length > 3) {
                endlessRecyclerViewScrollListener.resetState()
                adapter.clearItems()
                animeViewModel.searchAnime(searchField.text.toString(),1)
                searchMode = true
            } else {
                searchMode = false
                Toast.makeText(activity, "3 or more letters!", Toast.LENGTH_SHORT).show()
            }
        }
        searchField.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                adapter.clearItems()
                animeViewModel.searchAnime(searchField.text.toString(),1)
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }
}
