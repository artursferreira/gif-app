package com.artur.gifapp.di

import com.artur.gifapp.favourite.FavouriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favouriteModule = module {
    viewModel { FavouriteViewModel(giphyRepository = get()) }
}