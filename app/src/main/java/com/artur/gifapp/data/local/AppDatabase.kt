package com.artur.gifapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(GifItem::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDao
}