package com.dhizak.animefinder.ui.lists.adapters

interface BaseAdapter<T> {

    fun addItems(items : T?)

    fun insertNewItems(items : T?)

    fun clearItems()
}