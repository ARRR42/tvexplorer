package com.example.tvexplorer.core_network.com.example.tvexplorer.core_network.model

import kotlinx.serialization.Serializable

@Serializable
data class TvShowAliasCountry(
    val name: String?,
    val code: String?,
    val timezone: String?
)
