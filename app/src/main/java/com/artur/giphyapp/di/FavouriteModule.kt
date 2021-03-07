package com.artur.giphyapp.di

import com.artur.giphyapp.favourite.FavouriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favouriteModule = module {
    viewModel { FavouriteViewModel(giphyRepository = get()) }
}