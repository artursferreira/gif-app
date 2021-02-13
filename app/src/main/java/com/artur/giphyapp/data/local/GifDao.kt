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
    fun getAllFavourites(): LiveData<List<GifItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg gif: GifItem)

    @Query("UPDATE gifitem SET isTrending = 0 WHERE id NOT IN (:gifList) ")
    fun updateOldTrending(gifList: List<String>)

    @Update
    fun update(vararg gif: GifItem)

    @Delete
    fun delete(gif: GifItem)

}