package com.example.tvexplorer.core_network.com.example.tvexplorer.core_network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkShowInfo(
    val id: Long,
    val name: String?,
    val status: String?,
    val summary: String?,
    val image: NetworkTvShowImage?,
    val rating: NetworkTvShowRating?,
)
