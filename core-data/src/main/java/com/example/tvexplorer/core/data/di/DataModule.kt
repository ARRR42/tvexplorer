package com.example.tvexplorer.core.data.di

import com.example.tvexplorer.core.data.repository.FavoriteTvShowsRepository
import com.example.tvexplorer.core.data.repository.LocalFavoriteTvShowsRepository
import com.example.tvexplorer.core.data.repository.NetworkTvShowsRepository
import com.example.tvexplorer.core.data.repository.TvShowsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsTvShowsRepository(
        tvShowsRepository: NetworkTvShowsRepository,
    ): TvShowsRepository

    @Binds
    fun bindsFavoriteTvShowsRepository(
        favoriteTvShowsRepository: LocalFavoriteTvShowsRepository,
    ): FavoriteTvShowsRepository
}
