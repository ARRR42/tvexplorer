package com.example.tvexplorer.core_network

import com.example.tvexplorer.core_network.com.example.tvexplorer.core_network.model.NetworkShowInfo
import com.example.tvexplorer.core_network.com.example.tvexplorer.core_network.model.NetworkTvShowAlias
import com.example.tvexplorer.core_network.model.NetworkTvShow

interface TvExplorerNetworkDataSource {
    suspend fun getTvShows(query: String): List<NetworkTvShow>
    suspend fun getTvShow(id: Long): NetworkShowInfo
    suspend fun getTvShowAliases(id: Long): List<NetworkTvShowAlias>
}
