package com.artur.giphyapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GifDao {

    @Query("SELECT * FROM gifitem")
    fun getAll(): LiveData<List<GifItem>>

    @Query("SELECT * FROM gifitem WHERE isTrending == 1")
    fun getAllTrending(): LiveData<List<GifItem>>

    @Query("SELECT * FROM gifitem WHERE isFavourite == 1")
    fun getAllFavorites(): LiveData<List<GifItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg gif: GifItem)

    @Query("DELETE from gifitem WHERE isTrending == 1")
    fun deleteAllTrending()

    @Delete
    fun delete(gif: GifItem)

}