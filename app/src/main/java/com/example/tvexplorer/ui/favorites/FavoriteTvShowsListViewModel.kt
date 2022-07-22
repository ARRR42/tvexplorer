package com.example.tvexplorer.com.example.tvexplorer.ui.favorites

import androidx.lifecycle.viewModelScope
import com.example.tvexplorer.core.common.Result
import com.example.tvexplorer.core.common.asResult
import com.example.tvexplorer.core.data.repository.FavoriteTvShowsRepository
import com.example.tvexplorer.core.model.TvShow
import com.example.tvexplorer.navigation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FavoriteTvShowsListViewModel @Inject constructor(
    private val favoriteTvShowsRepository: FavoriteTvShowsRepository,
) : BaseViewModel() {

    private val favoriteShowsFlow = favoriteTvShowsRepository.getFavoriteTvShows().asResult()
    private val _uiState = MutableStateFlow(FavoriteTvShowsListScreenUiState(emptyList(), true, null))
    val uiState = _uiState.combine(favoriteShowsFlow) { state, result ->
        when (result) {
            is Result.Error -> {
                state.copy(errorMessage = "Error", isFetchingTvShows = false)
            }
            is Result.Loading -> {
                state.copy(isFetchingTvShows = true)
            }
            is Result.Success -> {
                state.copy(tvShowsList = result.data, isFetchingTvShows = false)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FavoriteTvShowsListScreenUiState(emptyList(), true, null)
    )

    fun onErrorMessageDismissed() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}

data class FavoriteTvShowsListScreenUiState(
    val tvShowsList: List<TvShow>,
    val isFetchingTvShows: Boolean,
    val errorMessage: String?,
)