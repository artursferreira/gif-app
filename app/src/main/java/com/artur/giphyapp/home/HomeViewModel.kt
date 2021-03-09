package com.artur.giphyapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.data.remote.Result
import com.artur.giphyapp.data.remote.giphy.repository.GiphyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val giphyRepository: GiphyRepository
) : ViewModel() {

    private val _gifs = MutableLiveData<Result<List<GifItem>>>()
    val gifs: LiveData<Result<List<GifItem>>>
        get() = _gifs

    val favouriteGifs = giphyRepository.favouriteGifs

    init {
        getTrendingGifs()
    }

    private fun getTrendingGifs() {
        viewModelScope.launch {
            val trendingGifs = withContext(Dispatchers.IO) { giphyRepository.getTrendingGifs() }
            withContext(Dispatchers.Main) {
                _gifs.postValue(trendingGifs)
            }
        }
    }

    fun search(query: String?) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                if (!query.isNullOrEmpty()) giphyRepository.search(query) else giphyRepository.getTrendingGifs()
            }
            withContext(Dispatchers.Main) {
                _gifs.postValue(result)
            }
        }
    }

    fun saveFavourite(gifItem: GifItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                giphyRepository.saveFavourite(gifItem)
            }
        }
    }

}