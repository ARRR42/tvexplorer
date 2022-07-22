package com.example.tvexplorer.core.database

import com.example.tvexplorer.core.database.dao.FavoriteTvShowDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesTvShowDao(
        database: TvExplorerDatabase,
    ): FavoriteTvShowDao = database.favoriteTvShowDao()
}
