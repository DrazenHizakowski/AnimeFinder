package com.dhizak.animefinder.ui.controllers

import android.arch.lifecycle.Observer
import android.support.v7.widget.*
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dhizak.animefinder.R
import com.dhizak.animefinder.model.Top
import com.dhizak.animefinder.ui.lists.adapters.AnimeListAdapter
import com.dhizak.animefinder.ui.viewmodel.AnimeListViewModel
import com.dhizak.animefinder.ui.widgets.EndlessRecyclerViewScrollListener
import work.beltran.conductorviewmodel.ViewModelController
import android.view.inputmethod.EditorInfo
import android.widget.TextView



class TopListController : ViewModelController() {

    val TAG = "TopListController"

    lateinit var clearButton: AppCompatImageView
    lateinit var searchButton: AppCompatImageView
    lateinit var filterButton: AppCompatImageView
    lateinit var searchField: AppCompatEditText
    lateinit var recyclerView: RecyclerView
    private lateinit var endlessRecyclerViewScrollListener : EndlessRecyclerViewScrollListener
    val adapter = AnimeListAdapter()
    lateinit var animeViewModel: AnimeListViewModel
    var searchMode = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.top_anime_list, container, false)
        clearButton = view.findViewById(R.id.clearButton)
        searchButton = view.findViewById(R.id.searchButton)
        filterButton = view.findViewById(R.id.filterList)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchField = view.findViewById(R.id.searchField)
        setAdapter()
        setClickListeners()
        animeViewModel = viewModelProvider().get(AnimeListViewModel::class.java)

        animeViewModel.getTopAnime(1).observe(this, Observer<MutableList<Top>> {
            adapter.addItems(it)
        })
        return view
    }

    fun setAdapter(){
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        endlessRecyclerViewScrollListener =  object : EndlessRecyclerViewScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if(searchMode){
                    animeViewModel.searchAnime(page = page,query = searchField.text.toString())
                }else{
                    animeViewModel.getTopAnime(page = page)
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
        searchButton.setOnClickListener {
            if (searchField.text.toString().length > 3) {
                adapter.clearItems()

                animeViewModel.searchAnime(searchField.text.toString(),1).observe(this, Observer {
                    adapter.clearItems()
                    adapter.addItems(it)
                })
            } else {
                Toast.makeText(activity, "3 or more letters!", Toast.LENGTH_SHORT).show()
            }
        }
        searchField.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    adapter.clearItems()
                    animeViewModel.searchAnime(searchField.text.toString(),1)
                    return true
                }
                return false
            }
        })
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }
}
