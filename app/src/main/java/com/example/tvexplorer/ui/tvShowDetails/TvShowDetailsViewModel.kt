package com.example.tvexplorer.com.example.tvexplorer.ui.tvShowDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.tvexplorer.com.example.tvexplorer.tools.Const
import com.example.tvexplorer.core.common.Result
import com.example.tvexplorer.core.common.asResult
import com.example.tvexplorer.core.data.repository.FavoriteTvShowsRepository
import com.example.tvexplorer.core.data.repository.TvShowsRepository
import com.example.tvexplorer.core.model.TvShowExtended
import com.example.tvexplorer.navigation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val tvShowsRepository: TvShowsRepository,
    private val favoriteTvShowsRepository: FavoriteTvShowsRepository,
) : BaseViewModel() {

    private val tvShowId: Long
    private val tvShowTitle: String
    private val fromFavorites: Boolean

    init {
        val args = TvShowDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)
        tvShowId = args.tvShowId
        tvShowTitle = args.tvShowTitle
        fromFavorites = args.fromFavorites
        Timber.tag(Const.TAG).i("TvShowDetailsViewModel. frtom fava $fromFavorites ")
    }

    private val tvShowFlow = if (fromFavorites) favoriteTvShowsRepository.getFavoriteTvShow(tvShowId).asResult() else tvShowsRepository.getTvShow(tvShowId).asResult()
    private val tvShowAliasesFlow = tvShowsRepository.getTvShowAliases(tvShowId).asResult()
    private val isFavoriteTvShowFlow = favoriteTvShowsRepository.isFavoriteTvShow(tvShowId)

    val uiState = combine(tvShowFlow, isFavoriteTvShowFlow, tvShowAliasesFlow) { tvShowResult, isFavorite, tvShowAliases ->
        Timber.tag(Const.TAG).i("TvShowDetailsViewModel. $tvShowResult $isFavorite $tvShowAliases")
        when (tvShowResult) {
            is Result.Error -> {
                TvShowDetailsScreenUiState(TvShowDetailsUiState.Error("Error"), tvShowTitle)
            }
            is Result.Loading -> {
                TvShowDetailsScreenUiState(TvShowDetailsUiState.Loading, tvShowTitle)
            }
            is Result.Success -> {
                val aliases = (tvShowAliases as? Result.Success)?.data.orEmpty().map { it.name }
                TvShowDetailsScreenUiState(TvShowDetailsUiState.Success(tvShowResult.data.copy(isFavorite = isFavorite), aliases = aliases), tvShowTitle)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TvShowDetailsScreenUiState(TvShowDetailsUiState.Loading, tvShowTitle)
    )

    fun onFavoriteClicked() {
        viewModelScope.launch {
            (uiState.value.state as? TvShowDetailsUiState.Success)?.tvShow?.let {
                favoriteTvShowsRepository.toggleFavoriteTvShow(it)
            }
        }
    }
}

sealed class TvShowDetailsUiState {
    data class Success(val tvShow: TvShowExtended, val aliases: List<String>) : TvShowDetailsUiState()
    object Loading : TvShowDetailsUiState()
    data class Error(val message: String) : TvShowDetailsUiState()
}

data class TvShowDetailsScreenUiState(
    val state: TvShowDetailsUiState,
    val screenTitle: String,
)