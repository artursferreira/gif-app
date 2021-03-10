package com.artur.gifapp.di

import com.artur.gifapp.data.remote.giphy.GiphyService
import com.artur.gifapp.data.remote.giphy.datasource.GiphyRemoteDataSource
import com.artur.gifapp.data.remote.giphy.repository.GiphyRepository
import com.artur.gifapp.utils.Constants.Companion.BASE_URL
import org.koin.dsl.module

val giphyModule = module {

    single { provideRetrofit(okHttpClient = get(), BASE_URL) }

    factory { createWebService<GiphyService>(retrofit = get()) }

    single { GiphyRemoteDataSource(giphyService = get()) }

    single { GiphyRepository(dao = get(), remoteSource = get()) }
}