package com.dhizak.animefinder.util.image

import android.support.annotation.DrawableRes
import android.support.v7.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dhizak.animefinder.AnimeFinderApplication



class ImageLoader(val centerCrop: Boolean = false,
                  val fitCenter: Boolean = false,
                  val centerInside : Boolean = false,
                  @DrawableRes val placeholder: Int = 0,
                  val imagePath : String,
                  val imageHolder : AppCompatImageView) {

    open fun loadImage() {
        val options = RequestOptions()
        if(centerCrop) options.centerCrop()
        if(fitCenter) options.fitCenter()
        if(centerInside) options.centerInside()
        if(placeholder != 0) options.placeholder(placeholder)

        Glide.with(AnimeFinderApplication.INSTANCE).load(imagePath).apply(options).into(imageHolder)
    }

}