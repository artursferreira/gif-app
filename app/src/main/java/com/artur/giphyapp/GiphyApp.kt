package com.artur.giphyapp

import android.app.Application
import com.artur.giphyapp.di.giphyModule
import com.artur.giphyapp.di.dbModule
import com.artur.giphyapp.di.favouriteModule
import com.artur.giphyapp.di.homeModule
import com.artur.giphyapp.di.mainModule
import com.artur.giphyapp.di.networkModule
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