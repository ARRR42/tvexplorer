package com.example.tvexplorer.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tvexplorer.core.database.dao.FavoriteTvShowDao
import com.example.tvexplorer.core.database.model.TvShowEntity

@Database(
    entities = [
        TvShowEntity::class
    ],
    version = 2,
    exportSchema = true,
)
@TypeConverters()
abstract class TvExplorerDatabase : RoomDatabase() {
    abstract fun favoriteTvShowDao(): FavoriteTvShowDao
}