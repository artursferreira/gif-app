package com.artur.gifapp.data.remote.giphy.datasource

import com.artur.gifapp.base.BaseDataSource
import com.artur.gifapp.data.remote.giphy.GiphyService

class GiphyRemoteDataSource(private val giphyService: GiphyService) : BaseDataSource() {
    suspend fun getTrending() = getResult { giphyService.getTrending() }

    suspend fun search(query: String?) = getResult {
        giphyService.search(query = query)
    }
}