package com.artur.giphyapp.ui


import com.artur.giphyapp.data.remote.giphy.datasource.GiphyRemoteDataSource
import com.artur.giphyapp.data.remote.giphy.repository.GiphyRepository
import org.koin.dsl.module

val mainModule = module {
    single { GiphyRepository(giphyDao = get(), giphyRemoteDataSource = get()) }
    single { GiphyRemoteDataSource(giphyService = get()) }
}