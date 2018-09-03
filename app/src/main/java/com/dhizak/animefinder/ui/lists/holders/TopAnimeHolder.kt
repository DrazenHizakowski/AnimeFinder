package com.dhizak.animefinder.ui.lists.holders

import android.view.View
import com.dhizak.animefinder.model.Top
import com.dhizak.animefinder.util.image.ImageLoader
import kotlinx.android.synthetic.main.top_anime_list_item.view.*

class TopAnimeHolder(itemView: View?) : BaseViewHolder<Top>(itemView) {

    override fun bind(holderItem: Top) {
        itemView.animeTitle.text = holderItem.title
        itemView.animeType.text = holderItem.type
        itemView.animeDescription.text = holderItem.description
        itemView.animeImage.setImageURI(holderItem.image_url)
    }

}