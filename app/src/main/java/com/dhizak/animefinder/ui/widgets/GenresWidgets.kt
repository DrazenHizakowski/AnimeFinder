package com.dhizak.animefinder.ui.widgets

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import com.dhizak.animefinder.R
import com.dhizak.animefinder.model.Genre

class GenresWidgets : GridView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    fun addGenres(genres: List<Genre>) {
        adapter = GenresAdapter(context,genres)
    }

    override fun onMeasure(widthMeasureSpec : Int,heightMeasureSpec : Int) {
        val heightSpec : Int

        if (layoutParams.height == LayoutParams.WRAP_CONTENT) {
            // The great Android "hackatlon", the love, the magic.
            // The two leftmost bits in the height measure spec have
            // a special meaning, hence we can't use them to describe height.
            heightSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE shr  2, MeasureSpec.AT_MOST)
        }
        else {
            // Any other height should be respected as is.
            heightSpec = heightMeasureSpec;
        }

        super.onMeasure(widthMeasureSpec, heightSpec);
    }

    class GenreItem : CardView {

        constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
            addView(LayoutInflater.from(context).inflate(R.layout.ganre_item, null, false))
        }

        constructor(context: Context) : super(context) {
            val view = LayoutInflater.from(context).inflate(R.layout.ganre_item, null, false)
            addView(view)
        }

        fun setText(text : String){
            findViewById<TextView>(R.id.genreName).text = text
        }
    }

    class GenresAdapter(val context: Context,val genres: List<Genre>) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val genreItem : GenreItem
            if(convertView == null){
                genreItem = GenreItem(context)
                genreItem.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            }else{
                genreItem = convertView as GenreItem
            }
            genreItem.setText(genres[position].title)
            return genreItem
        }

        override fun getItem(position: Int): Any? = null

        override fun getItemId(position: Int): Long = 0

        override fun getCount(): Int {
            return genres.size
        }

    }
}