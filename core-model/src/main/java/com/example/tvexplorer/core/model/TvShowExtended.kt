package com.example.tvexplorer.core.model

data class TvShowExtended(
    val id: Long,
    val title: String,
    val status: String,
    val summary: String,
    val imageUrl: String,
    val rating: Float,
    val isFavorite: Boolean = false,
)