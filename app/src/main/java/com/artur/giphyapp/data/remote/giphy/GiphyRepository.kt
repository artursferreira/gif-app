package com.artur.giphyapp.data.remote.giphy

import com.artur.giphyapp.data.remote.model.GifResult

class GiphyRepository(private val giphyApi: GiphyApi) {
    suspend fun getTrending(): GifResult {
        return giphyApi.getTrending()
    }
}