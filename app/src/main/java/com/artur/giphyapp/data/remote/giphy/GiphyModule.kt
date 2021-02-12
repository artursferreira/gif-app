package com.artur.giphyapp.data.remote.giphy

import com.artur.giphyapp.utils.Constants.Companion.BASE_URL
import com.artur.giphyapp.data.createWebService
import com.artur.giphyapp.data.provideRetrofit
import com.artur.giphyapp.data.remote.giphy.datasource.GiphyRemoteDataSource
import org.koin.dsl.module

val giphyModule = module {

    single { provideRetrofit(okHttpClient = get(), BASE_URL) }

    factory { createWebService<GiphyService>(retrofit = get()) }

    single { GiphyRemoteDataSource(giphyService = get()) }
}