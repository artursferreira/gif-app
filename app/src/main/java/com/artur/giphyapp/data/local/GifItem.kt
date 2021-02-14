package com.artur.giphyapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favourites")
data class GifItem(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    val width: Int,
    val height: Int,
    var isFavourite: Boolean = false
)