package com.artur.gifapp.extensions

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.artur.gifapp.R
import com.artur.gifapp.data.local.GifItem
import com.artur.gifapp.data.remote.model.GifResult

fun GifResult.mapToGifItem(): List<GifItem> {
    return this.data.map {
        GifItem(
            id = it.id,
            title = it.title,
            width = it.images.fixedHeight.width,
            height = it.images.fixedHeight.height,
            gifUrl = it.images.fixedHeight.gifUrl,
            originalGifUrl = it.images.original.gifUrl,
            webpUrl = it.images.fixedHeight.webpUrl
        )
    }
}

fun View.setLayoutSize(width: Int, height: Int) {
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

fun Context.getAppTheme(selectedTheme: String): Int {
    return when (selectedTheme) {
        getString(R.string.pref_night_on) -> {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        getString(R.string.pref_night_off) -> {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        else -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            } else {
                AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            }
        }
    }
}