package com.example.tvexplorer.core.data.repository

import com.example.tvexplorer.core.data.model.asExternalModel
import com.example.tvexplorer.core.data.model.asExternalModelExtended
import com.example.tvexplorer.core.model.TvShow
import com.example.tvexplorer.core.model.TvShowAlias
import com.example.tvexplorer.core.model.TvShowExtended
import com.example.tvexplorer.core_network.TvExplorerNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkTvShowsRepository @Inject constructor(
    private val network: TvExplorerNetworkDataSource,
) : TvShowsRepository {
    override fun getTvShows(query: String): Flow<List<TvShow>> {
        return flow {
            emit(network.getTvShows(query).map { it.asExternalModel() })
        }
    }

    override fun getTvShow(id: Long): Flow<TvShowExtended> {
        return flow {
            emit(network.getTvShow(id).asExternalModelExtended())
        }
    }

    override fun getTvShowAliases(id: Long): Flow<List<TvShowAlias>> {
        return flow {
            emit(network.getTvShowAliases(id).map { it.asExternalModel() })
        }
    }
}
