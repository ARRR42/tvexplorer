package com.example.tvexplorer.core_network.retrofit

import com.example.tvexplorer.core_network.TvExplorerNetworkDataSource
import com.example.tvexplorer.core_network.com.example.tvexplorer.core_network.model.NetworkShowInfo
import com.example.tvexplorer.core_network.com.example.tvexplorer.core_network.model.NetworkTvShowAlias
import com.example.tvexplorer.core_network.model.NetworkTvShow
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val TvShowsBaseUrl = "https://api.tvmaze.com/"

private interface RetrofitTvExplorerNetworkApi {
    @GET(value = "search/shows")
    suspend fun getTvShows(
        @Query("q") query: String,
    ): List<NetworkTvShow>

    @GET(value = "shows/{id}")
    suspend fun getTvShow(
        @Path("id") id: Long,
    ): NetworkShowInfo

    @GET(value = "shows/{id}/akas")
    suspend fun getTvShowAliases(
        @Path("id") id: Long,
    ): List<NetworkTvShowAlias>
}

class RetrofitTvExplorerNetwork(
    networkJson: Json,
) : TvExplorerNetworkDataSource {

    private val contentType = "application/json".toMediaType()
    private val networkApi = Retrofit.Builder()
        .baseUrl(TvShowsBaseUrl)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                .build()
        )
        .addConverterFactory(networkJson.asConverterFactory(contentType))
        .build()
        .create(RetrofitTvExplorerNetworkApi::class.java)

    override suspend fun getTvShows(query: String): List<NetworkTvShow> =
        networkApi.getTvShows(query = query)

    override suspend fun getTvShow(id: Long): NetworkShowInfo =
        networkApi.getTvShow(id = id)

    override suspend fun getTvShowAliases(id: Long): List<NetworkTvShowAlias> =
        networkApi.getTvShowAliases(id = id)
}

