package com.artur.giphyapp

import android.app.Application
import com.artur.giphyapp.data.remote.giphy.giphyModule
import com.artur.giphyapp.ui.dbModule
import com.artur.giphyapp.ui.favourite.favouriteModule
import com.artur.giphyapp.ui.home.homeModule
import com.artur.giphyapp.ui.mainModule
import com.artur.giphyapp.ui.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GiphyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(mainModule, networkModule, dbModule, giphyModule, mainModule, homeModule, favouriteModule))
        }
    }
}