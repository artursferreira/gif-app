package com.artur.giphyapp.data.remote.giphy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.artur.giphyapp.data.local.GifDao
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.data.remote.Result
import com.artur.giphyapp.data.remote.giphy.datasource.GiphyRemoteDataSource
import com.artur.giphyapp.data.remote.model.GifResult
import com.artur.giphyapp.extensions.mapToGifItem
import com.artur.giphyapp.extensions.resultLiveData
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

class GiphyRepository(
    private val dao: GifDao,
    private val remoteSource: GiphyRemoteDataSource
) {

    val trendingGifs: LiveData<Result<List<GifItem>>> = liveData(Dispatchers.IO) {
        emit(Result.loading<List<GifItem>>())
        val trending = remoteSource.getTrending()

        if (trending.status == Result.Status.SUCCESS) {
            emit(Result.success(trending.data?.mapToGifItem() ?: listOf()))
        } else if (trending.status == Result.Status.ERROR) {
            emit(Result.error(trending.message!!))
        }

    }

    val favouriteGifs = dao.getAllFavourites()

    suspend fun search(query: String?): List<GifItem>? {
        return remoteSource.search(query).data?.mapToGifItem()
    }

    suspend fun saveFavourite(gifItem: GifItem) {
        dao.insertAll(gifItem)
    }

}