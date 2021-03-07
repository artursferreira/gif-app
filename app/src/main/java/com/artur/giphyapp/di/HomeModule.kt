package com.artur.giphyapp.di

import com.artur.giphyapp.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    viewModel { HomeViewModel(giphyRepository = get()) }
}