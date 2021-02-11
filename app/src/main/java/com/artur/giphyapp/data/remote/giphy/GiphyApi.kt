package com.artur.giphyapp.data.remote.giphy

import com.artur.giphyapp.config.Config
import com.artur.giphyapp.data.remote.model.GifResult
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("v1/gifs/trending")
    suspend fun getTrending(
        @Query("api_key") apiKey: String = Config.KEY,
        @Query("limit") limit: String = "25",
        @Query("rating") rating: String = ""
    ): GifResult

}