package com.example.tvexplorer.core.data.repository

import com.example.tvexplorer.core.model.TvShow
import com.example.tvexplorer.core.model.TvShowExtended
import kotlinx.coroutines.flow.Flow

interface FavoriteTvShowsRepository {
    fun getFavoriteTvShows(): Flow<List<TvShow>>
    fun getFavoriteTvShow(id: Long): Flow<TvShowExtended>
    fun isFavoriteTvShow(id: Long): Flow<Boolean>
    suspend fun addFavoriteTvShow(tvShowExtended: TvShowExtended)
    suspend fun removeFavoriteTvShow(tvShowId: Long)
    suspend fun toggleFavoriteTvShow(tvShowExtended: TvShowExtended)
}
