package com.example.tvexplorer.core.data.repository

import com.example.tvexplorer.core.model.TvShow
import com.example.tvexplorer.core.model.TvShowAlias
import com.example.tvexplorer.core.model.TvShowExtended
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {
    fun getTvShows(query: String): Flow<List<TvShow>>
    fun getTvShow(id: Long): Flow<TvShowExtended>
    fun getTvShowAliases(id: Long): Flow<List<TvShowAlias>>
}
