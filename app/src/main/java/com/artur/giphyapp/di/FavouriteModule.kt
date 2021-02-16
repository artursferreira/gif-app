package com.artur.giphyapp.di

import com.artur.giphyapp.ui.favourite.FavouriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favouriteModule = module {
    viewModel { FavouriteViewModel(giphyRepository = get()) }
}