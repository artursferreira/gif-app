package com.artur.giphyapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GifItem(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    var isTrending: Boolean = false,
    var isFavourite: Boolean = false
)