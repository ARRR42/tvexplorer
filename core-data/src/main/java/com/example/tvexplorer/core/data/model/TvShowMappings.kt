package com.example.tvexplorer.core.data.model

import com.example.tvexplorer.core.database.model.TvShowEntity
import com.example.tvexplorer.core.model.TvShow
import com.example.tvexplorer.core.model.TvShowAlias
import com.example.tvexplorer.core.model.TvShowExtended
import com.example.tvexplorer.core_network.com.example.tvexplorer.core_network.model.NetworkShowInfo
import com.example.tvexplorer.core_network.com.example.tvexplorer.core_network.model.NetworkTvShowAlias
import com.example.tvexplorer.core_network.model.NetworkTvShow

const val noId = -1L

fun TvShowExtended.asEntity() = TvShowEntity(
    id = id,
    title = title,
    status = status,
    imageUrl = imageUrl,
    summary = summary,
    rating = rating
)

fun TvShowEntity.asExternalModel() = TvShow(
    id = id,
    title = title,
    status = status,
    imageUrl = imageUrl
)

fun TvShowEntity.asExternalModelExtended() = TvShowExtended(
    id = id,
    title = title,
    status = status,
    imageUrl = imageUrl,
    summary = summary,
    rating = rating
)

fun NetworkTvShow.asExternalModel() = TvShow(
    id = show?.id ?: noId,
    title = show?.name.orEmpty(),
    status = show?.status.orEmpty(),
    imageUrl = show?.image?.medium.orEmpty()
)

fun NetworkShowInfo.asExternalModelExtended() = TvShowExtended(
    id = id,
    title = name.orEmpty(),
    status = status.orEmpty(),
    summary = summary.orEmpty(),
    imageUrl = image?.medium.orEmpty(),
    rating = rating?.average ?: 0f
)

fun NetworkTvShowAlias.asExternalModel() = TvShowAlias(
   name = name.orEmpty(),
)