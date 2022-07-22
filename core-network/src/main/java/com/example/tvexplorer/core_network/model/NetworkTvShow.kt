package com.example.tvexplorer.core_network.model

import com.example.tvexplorer.core_network.com.example.tvexplorer.core_network.model.NetworkShowInfo
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTvShow(
    val score: Float?,
    val show: NetworkShowInfo?,
)