package com.artur.giphyapp.data.remote.giphy.datasource

import com.artur.giphyapp.base.BaseDataSource
import com.artur.giphyapp.data.remote.giphy.GiphyService

class GiphyRemoteDataSource(private val giphyService: GiphyService) : BaseDataSource() {
    suspend fun getTrending() = getResult { giphyService.getTrending() }

    suspend fun search(query: String?) = getResult {
        giphyService.search(query = query)
    }
}