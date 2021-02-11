package com.artur.giphyapp.data.remote.giphy

import com.artur.giphyapp.data.remote.NetworkResult
import com.artur.giphyapp.data.remote.model.GifResult
import com.artur.giphyapp.extensions.handleException

class GiphyRepository(private val giphyApi: GiphyApi) {
    suspend fun getTrending(): NetworkResult<GifResult> {
        return handleException { giphyApi.getTrending() }
    }
}