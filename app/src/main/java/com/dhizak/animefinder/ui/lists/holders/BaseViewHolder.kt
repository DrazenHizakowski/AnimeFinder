package com.dhizak.animefinder.ui.lists.holders

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<T>(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    lateinit var holderItem : Any

    abstract fun bind(holderItem : T)
}