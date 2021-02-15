package com.artur.giphyapp.data.remote.giphy

import com.artur.giphyapp.utils.Constants.Companion.BASE_URL
import com.artur.giphyapp.data.remote.giphy.datasource.GiphyRemoteDataSource
import com.artur.giphyapp.data.remote.giphy.repository.GiphyRepository
import com.artur.giphyapp.ui.createWebService
import com.artur.giphyapp.ui.provideRetrofit
import org.koin.dsl.module

val giphyModule = module {

    single { provideRetrofit(okHttpClient = get(), BASE_URL) }

    factory { createWebService<GiphyService>(retrofit = get()) }

    single { GiphyRemoteDataSource(giphyService = get()) }

    single { GiphyRepository(dao = get(), remoteSource = get()) }
}