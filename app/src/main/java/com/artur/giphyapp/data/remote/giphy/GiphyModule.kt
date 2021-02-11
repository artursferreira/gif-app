package com.artur.giphyapp.data.remote.giphy

import com.artur.giphyapp.config.Constants.Companion.BASE_URL
import com.artur.giphyapp.data.createWebService
import com.artur.giphyapp.data.provideRetrofit
import org.koin.dsl.module

val giphyModule = module {

    single { provideRetrofit(okHttpClient = get(), BASE_URL) }

    factory { createWebService<GiphyApi>(retrofit = get()) }

    single { GiphyRepository(giphyApi = get()) }
}