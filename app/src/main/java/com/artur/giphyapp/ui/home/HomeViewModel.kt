package com.artur.giphyapp.ui.home

import androidx.lifecycle.ViewModel
import com.artur.giphyapp.data.remote.giphy.GiphyRepository
import com.artur.giphyapp.manager.CoroutinesManager
import kotlinx.coroutines.launch

class HomeViewModel(
    private val coroutinesManager: CoroutinesManager,
    private val giphyRepository: GiphyRepository
) : ViewModel() {

    fun getTrending() {
        coroutinesManager.ioScope.launch {
            giphyRepository.getTrending()
        }
    }
}