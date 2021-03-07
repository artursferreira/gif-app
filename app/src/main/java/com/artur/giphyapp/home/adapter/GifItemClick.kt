package com.artur.giphyapp.home.adapter

import com.artur.giphyapp.data.local.GifItem

class GifItemClick(val block: (GifItem) -> Unit) {
    /**
     * Called when a asteroid is clicked
     *
     * @param gifItem the gif that was clicked
     */
    fun onClick(gifItem: GifItem) = block(gifItem)
}