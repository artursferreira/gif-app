package com.artur.gifapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GifDao {

    @Query("SELECT * FROM Favourites")
    fun getAll(): LiveData<List<GifItem>>

    @Query("SELECT * FROM Favourites WHERE isFavourite == 1")
    fun getAllFavourites(): LiveData<List<GifItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg gif: GifItem)

    @Update
    fun update(vararg gif: GifItem)

    @Delete
    fun delete(gif: GifItem)

    @Query("DELETE FROM Favourites WHERE isFavourite == 1")
    fun deleteAllFavourites()

}