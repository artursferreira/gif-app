package com.artur.gifapp.data.remote.giphy.repository

import com.artur.gifapp.data.local.GifDao
import com.artur.gifapp.data.local.GifItem
import com.artur.gifapp.data.remote.Result
import com.artur.gifapp.data.remote.giphy.datasource.GiphyRemoteDataSource
import com.artur.gifapp.data.remote.succeeded
import com.artur.gifapp.extensions.mapToGifItem

class GiphyRepository(
    private val dao: GifDao,
    private val remoteSource: GiphyRemoteDataSource
) {

    val favouriteGifs = dao.getAllFavourites()

    suspend fun getTrendingGifs(): Result<List<GifItem>> {
        val trending = remoteSource.getTrending()
        return if (trending.succeeded) {
            Result.Success((trending as Result.Success).data.mapToGifItem())
        } else Result.Error((trending as Result.Error).exception)
    }

    suspend fun search(query: String?): Result<List<GifItem>> {
        val searchResult = remoteSource.search(query)
        return if (searchResult.succeeded) {
            Result.Success((searchResult as Result.Success).data.mapToGifItem())
        } else Result.Error((searchResult as Result.Error).exception)

    }

    suspend fun saveFavourite(gifItem: GifItem) {
        dao.insertAll(gifItem)
    }

    suspend fun deleteAllFavourites() {
        dao.deleteAllFavourites()
    }

}