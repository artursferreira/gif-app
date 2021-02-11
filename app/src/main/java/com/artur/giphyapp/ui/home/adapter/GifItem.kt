package com.artur.giphyapp.ui.home.adapter

data class GifItem(
    val id: String,
    val title: String,
    val url: String,
    var isFavourite : Boolean = false
)