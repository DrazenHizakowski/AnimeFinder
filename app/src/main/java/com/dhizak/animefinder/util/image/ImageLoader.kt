package com.dhizak.animefinder.util.image

import android.content.Context
import android.widget.ImageView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

class ImageLoader {

    companion object {
        fun loadImage(context: Context, path: String, target: SimpleDraweeView) {
            if (!Fresco.hasBeenInitialized()) {
                Fresco.initialize(context)
            }
            target.setImageURI(path)
        }
    }
}