package com.artur.giphyapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GifItemLocal(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    var isFavorite: Boolean = false
)