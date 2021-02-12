package com.artur.giphyapp.extensions

import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.data.remote.model.GifResult

fun GifResult.mapToGifItem() : List<GifItem> {
    return this.data.map { GifItem(id = it.id, title = it.title, it.images.downsized.url) }
}