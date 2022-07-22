package com.example.tvexplorer.core_network

import android.content.Context
import com.example.tvexplorer.core_network.retrofit.RetrofitTvExplorerNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesNetworkModule(@ApplicationContext context: Context): TvExplorerNetworkDataSource =
        RetrofitTvExplorerNetwork(Json { ignoreUnknownKeys = true })
}
