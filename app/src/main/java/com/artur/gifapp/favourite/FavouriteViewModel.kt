package com.artur.gifapp.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artur.gifapp.data.local.GifItem
import com.artur.gifapp.data.remote.giphy.repository.GiphyRepository
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

    fun deleteAllFavourites() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                giphyRepository.deleteAllFavourites()
            }
        }
    }
}