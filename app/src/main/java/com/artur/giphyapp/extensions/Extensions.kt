package com.artur.giphyapp.extensions

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.data.remote.model.GifResult

fun GifResult.mapToGifItem(): List<GifItem> {
    return this.data.map {
        GifItem(
            id = it.id,
            title = it.title,
            width = it.images.downsized.width,
            height = it.images.downsized.height,
            url = it.images.downsized.url
        )
    }
}

fun View.setLayoutHeight(width: Int, height: Int) {
    layoutParams.apply {
        this.width = width * 3
        this.height = height * 3
    }
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


fun Fragment.setDisplayHomeAsUpEnabled(bool: Boolean) {
    if (activity is AppCompatActivity) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(
            bool
        )
    }
}