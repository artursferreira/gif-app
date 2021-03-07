package com.artur.giphyapp.home

import androidx.lifecycle.*
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

    init {
        getTrendingGifs()
    }

    fun getTrendingGifs() {
        viewModelScope.launch {
            val trendingGifs = withContext(Dispatchers.IO) { giphyRepository.getTrendingGifs() }
            val favouriteGifs: List<String> =
                withContext(Dispatchers.IO) { giphyRepository.getFavourites().map { it.id } }
            if (trendingGifs is Result.Success) {
                trendingGifs.data.forEach { gifItem ->
                    gifItem.isFavourite = favouriteGifs.contains(gifItem.id)
                }
            }
            _gifs.postValue(trendingGifs)
        }
    }

    fun search(query: String?) {
        viewModelScope.launch {
            val result = giphyRepository.search(query)
            _gifs.postValue(result)
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