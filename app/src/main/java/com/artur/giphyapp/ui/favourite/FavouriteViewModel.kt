package com.artur.giphyapp.ui.favourite

import androidx.lifecycle.ViewModel
import com.artur.giphyapp.data.remote.giphy.repository.GiphyRepository

class FavouriteViewModel(
    private val giphyRepository: GiphyRepository
) : ViewModel() {

    val favouriteGifs = giphyRepository.favouriteGifs
}