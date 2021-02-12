package com.artur.giphyapp.data.remote.giphy.repository

import com.artur.giphyapp.data.local.GifDao
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.data.remote.giphy.datasource.GiphyRemoteDataSource
import com.artur.giphyapp.extensions.mapToGifItem
import com.artur.giphyapp.extensions.resultLiveData

class GiphyRepository(
    private val dao: GifDao,
    private val remoteSource: GiphyRemoteDataSource
) {

    val trendingGifs = resultLiveData(
        databaseQuery = { dao.getAllTrending() },
        networkCall = { remoteSource.getTrending() },
        saveCallResult = {
            dao.deleteOldTrending(it.data.map { it.id })
            dao.insertAll(*it.mapToGifItem().map { it.copy(isTrending = true) }.toTypedArray())
        }
    )

    val favouriteGifs = dao.getAllFavourites()

    suspend fun search(query: String?) : List<GifItem>?  {
        return remoteSource.search(query).data?.mapToGifItem()
    }

    suspend fun saveFavourite(gifItem: GifItem) {
        dao.update(gifItem)
    }

}