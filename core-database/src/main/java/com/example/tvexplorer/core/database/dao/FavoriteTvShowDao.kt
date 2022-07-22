package com.example.tvexplorer.core.database.dao

import androidx.room.*
import com.example.tvexplorer.core.database.model.TvShowEntity
import com.example.tvexplorer.core.database.model.tableName
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTvShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: TvShowEntity)

    @Query("DELETE FROM $tableName where id == :tvShowId")
    suspend fun deleteById(tvShowId: Long)

    @Query("SELECT EXISTS(SELECT * FROM $tableName WHERE id = :tvShowId)")
    fun isFavoriteTvShow(tvShowId: Long): Flow<Boolean>

    @Query(value = "SELECT * FROM $tableName WHERE id = :tvShowId")
    fun getById(tvShowId: Long): Flow<TvShowEntity>

    @Query(value = "SELECT * FROM $tableName WHERE id = :tvShowId")
    suspend fun getByIdBlocked(tvShowId: Long): TvShowEntity?

    @Query(value = "SELECT * FROM $tableName")
    fun getStream(): Flow<List<TvShowEntity>>

    @Transaction
    suspend fun toggleFavoriteTvShow(tvShow: TvShowEntity) {
        val tvShowEntity = getByIdBlocked(tvShow.id)
        if (tvShowEntity == null) {
            insert(tvShow)
        } else {
            deleteById(tvShow.id)
        }
    }
}