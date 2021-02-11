package com.artur.giphyapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.artur.giphyapp.data.remote.Resource
import com.artur.giphyapp.data.remote.giphy.GiphyRepository
import com.artur.giphyapp.data.remote.model.Data
import com.artur.giphyapp.data.remote.model.GifResult
import com.artur.giphyapp.ui.home.adapter.GifItem
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val giphyRepository: GiphyRepository
) : ViewModel() {

    fun List<Data>.mapGifs() : List<GifItem> {
        return map { GifItem(id = it.id, title = it.title, it.images.downsized.url) }
    }

    fun getTrending() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = giphyRepository.getTrending().data.mapGifs()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun search(query: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = giphyRepository.search(query).data.mapGifs()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}