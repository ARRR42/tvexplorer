package com.example.tvexplorer.com.example.tvexplorer.ui.tvShowsList

import androidx.lifecycle.viewModelScope
import com.example.tvexplorer.core.data.repository.TvShowsRepository
import com.example.tvexplorer.core.model.TvShow
import com.example.tvexplorer.navigation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsListViewModel @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(TvShowsListScreenUiState(emptyList(), "", false, null))
    val uiState: StateFlow<TvShowsListScreenUiState> = _uiState.asStateFlow()

    private var fetchTvShowsJob: Job? = null
    fun submitQuery(text: CharSequence?) {
        fetchTvShowsJob?.cancel()
        val updatedQuery = text?.toString().orEmpty()
        _uiState.update {
            it.copy(currentQuery = updatedQuery)
        }
        fetchTvShowsJob = viewModelScope.launch {
            tvShowsRepository
                .getTvShows(_uiState.value.currentQuery)
                .catch { throwable ->
                    throwable.printStackTrace()
                    _uiState.update {
                        // TODO specific errors
                        it.copy(errorMessage = "Error")
                    }
                }
                .onStart {
                    _uiState.update {
                        it.copy(isFetchingTvShows = true)
                    }
                }
                .onCompletion {
                    _uiState.update {
                        it.copy(isFetchingTvShows = false)
                    }
                }
                .collect { response ->
                    _uiState.update {
                        it.copy(tvShowsList = response)
                    }
                }
        }
    }

    fun onErrorMessageDismissed() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}

data class TvShowsListScreenUiState(
    val tvShowsList: List<TvShow>,
    val currentQuery: String,
    val isFetchingTvShows: Boolean,
    val errorMessage: String?,
)

fun TvShowsListScreenUiState.showEmptyListMessage(): EmptyListMessageTypes {
    return if (tvShowsList.isNotEmpty() || isFetchingTvShows) {
        EmptyListMessageTypes.None
    } else {
        if (currentQuery.isBlank()) EmptyListMessageTypes.EmptyQuery else EmptyListMessageTypes.NothingFound
    }
}

enum class EmptyListMessageTypes {
    None,
    NothingFound,
    EmptyQuery
}