package com.artur.giphyapp.ui.home

import androidx.lifecycle.ViewModel
import com.artur.giphyapp.data.remote.giphy.repository.GiphyRepository

class HomeViewModel(
    private val giphyRepository: GiphyRepository
) : ViewModel() {

    val trendingGifs = giphyRepository.trendingGifs




}