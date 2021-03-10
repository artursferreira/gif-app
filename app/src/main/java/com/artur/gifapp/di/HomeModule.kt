package com.artur.gifapp.di

import com.artur.gifapp.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    viewModel { HomeViewModel(giphyRepository = get()) }
}