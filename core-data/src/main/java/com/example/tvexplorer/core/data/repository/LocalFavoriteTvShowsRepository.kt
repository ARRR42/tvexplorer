package com.example.tvexplorer.core.data.repository

import com.example.tvexplorer.core.data.model.asEntity
import com.example.tvexplorer.core.data.model.asExternalModel
import com.example.tvexplorer.core.data.model.asExternalModelExtended
import com.example.tvexplorer.core.database.dao.FavoriteTvShowDao
import com.example.tvexplorer.core.database.model.TvShowEntity
import com.example.tvexplorer.core.model.TvShow
import com.example.tvexplorer.core.model.TvShowExtended
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalFavoriteTvShowsRepository @Inject constructor(
    private val favoriteTvShowDao: FavoriteTvShowDao,
) : FavoriteTvShowsRepository {

    override fun getFavoriteTvShows(): Flow<List<TvShow>> =
        favoriteTvShowDao.getStream().map { it.map(TvShowEntity::asExternalModel) }

    override fun getFavoriteTvShow(id: Long): Flow<TvShowExtended> =
        favoriteTvShowDao.getById(id).map { it.asExternalModelExtended() }

    override suspend fun addFavoriteTvShow(tvShowExtended: TvShowExtended) {
        favoriteTvShowDao.insert(tvShowExtended.asEntity())
    }

    override suspend fun removeFavoriteTvShow(tvShowId: Long) {
        favoriteTvShowDao.deleteById(tvShowId)
    }

    override fun isFavoriteTvShow(id: Long): Flow<Boolean> =
        favoriteTvShowDao.isFavoriteTvShow(id)

    override suspend fun toggleFavoriteTvShow(tvShow: TvShowExtended) =
        favoriteTvShowDao.toggleFavoriteTvShow(tvShow.asEntity())
}
