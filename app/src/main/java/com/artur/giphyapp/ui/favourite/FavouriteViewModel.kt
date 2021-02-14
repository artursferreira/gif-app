package com.artur.giphyapp.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.data.remote.giphy.repository.GiphyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteViewModel(
    private val giphyRepository: GiphyRepository
) : ViewModel() {

    val favouriteGifs = giphyRepository.favouriteGifs

    fun saveFavourite(gifItem: GifItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                giphyRepository.saveFavourite(gifItem)
            }
        }
    }
}