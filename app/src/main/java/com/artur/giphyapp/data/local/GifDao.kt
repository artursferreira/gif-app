package com.artur.giphyapp.data.local

import androidx.room.*

@Dao
interface GifDao {

    @Query("SELECT * FROM gifitemlocal")
    fun getAll(): List<GifItemLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg gif: GifItemLocal)

    @Delete
    fun delete(gif: GifItemLocal)

}