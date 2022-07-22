package com.example.tvexplorer.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val tableName = "tv_shows"

@Entity(
    tableName = tableName,
)
data class TvShowEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val status: String,
    val imageUrl: String,
    val summary: String,
    val rating: Float,
)
