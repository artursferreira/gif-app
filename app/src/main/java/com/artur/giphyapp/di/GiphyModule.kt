package com.artur.giphyapp.di

import com.artur.giphyapp.data.remote.giphy.GiphyService
import com.artur.giphyapp.utils.Constants.Companion.BASE_URL
import com.artur.giphyapp.data.remote.giphy.datasource.GiphyRemoteDataSource
import com.artur.giphyapp.data.remote.giphy.repository.GiphyRepository
import org.koin.dsl.module

val giphyModule = module {

    single { provideRetrofit(okHttpClient = get(), BASE_URL) }

    factory { createWebService<GiphyService>(retrofit = get()) }

    single { GiphyRemoteDataSource(giphyService = get()) }

    single { GiphyRepository(dao = get(), remoteSource = get()) }
}