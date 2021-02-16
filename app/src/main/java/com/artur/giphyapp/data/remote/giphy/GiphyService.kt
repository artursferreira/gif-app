package com.artur.giphyapp.data.remote.giphy

import com.artur.giphyapp.utils.Config
import com.artur.giphyapp.data.remote.model.GifResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("v1/gifs/trending")
    suspend fun getTrending(
        @Query("api_key") apiKey: String = Config.KEY,
        @Query("limit") limit: String = DEFAULT_LIMIT,
        @Query("rating") rating: String = GENERAL
    ): Response<GifResult>

    @GET("v1/gifs/search")
    suspend fun search(
        @Query("api_key") apiKey: String = Config.KEY,
        @Query("q") query: String?,
        @Query("limit") limit: String = DEFAULT_LIMIT,
        @Query("rating") rating: String = GENERAL
    ): Response<GifResult>

    companion object {
        const val DEFAULT_LIMIT = "100"
        const val GENERAL = "g" //general audiences
    }

}