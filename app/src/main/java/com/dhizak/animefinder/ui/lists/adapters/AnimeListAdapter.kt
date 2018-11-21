package com.dhizak.animefinder.ui.lists.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dhizak.animefinder.R
import com.dhizak.animefinder.model.Top
import com.dhizak.animefinder.ui.lists.holders.TopAnimeHolder
import com.dhizak.animefinder.ui.lists.listeners.OnAnimeListListener

class AnimeListAdapter(var listener : OnAnimeListListener) : RecyclerView.Adapter<TopAnimeHolder>(), BaseAdapter<List<Top>> {

    val TAG = "AnimeListAdapter"

    private var items: MutableList<Top> = mutableListOf()

    override fun addItems(items: List<Top>?) {
        if (items != null) {
            val before = items.size
            this.items.addAll(items)
            notifyItemRangeInserted(before,this.items.size)
        }
    }

    override fun insertNewItems(items: List<Top>?) {
        this.items.clear()
        this.items.addAll(items!!)
        notifyDataSetChanged()
    }

    override fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAnimeHolder {
        return TopAnimeHolder(LayoutInflater.from(parent.context).inflate(R.layout.top_anime_list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: TopAnimeHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { listener.onAnimeSelected(items[position].mal_id!!) }
    }
}