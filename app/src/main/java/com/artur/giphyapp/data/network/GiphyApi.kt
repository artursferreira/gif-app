package com.artur.giphyapp.data.network

import com.artur.giphyapp.model.GifResult
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("v1/gifs/trending")
    suspend fun getTrending(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: String,
        @Query("rating") rating: String
    ): GifResult

}