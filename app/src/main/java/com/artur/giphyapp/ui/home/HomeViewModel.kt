package com.artur.giphyapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.artur.giphyapp.data.remote.Resource
import com.artur.giphyapp.data.remote.giphy.GiphyRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val giphyRepository: GiphyRepository
) : ViewModel() {

    fun getTrending() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = giphyRepository.getTrending()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}