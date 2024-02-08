package org.michaelbel.movies.details

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.palette.graphics.Palette
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.exceptions.MovieDetailsException
import org.michaelbel.movies.common.ktx.require
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.ScreenState
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.persistence.database.entity.MovieDb

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    networkManager: NetworkManager,
    private val interactor: Interactor
): BaseViewModel() {

    private val movieList: String? = savedStateHandle["movieList"]
    private val movieId: Int = savedStateHandle.require("movieId")

    private val _detailsState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading)
    val detailsState: StateFlow<ScreenState> = _detailsState.asStateFlow()

    // fixme R&D pass initial position

    /*val pagingDataFlow: Flow<PagingData<MovieDb>> = interactor.moviesPagingDataWithPosition(MovieList.transformNullable(movieList), pos)
        .cachedIn(this)*/

    val pagingDataFlow: Flow<PagingData<MovieDb>> = flowOf(PagingData.empty())

    val networkStatus: StateFlow<NetworkStatus> = networkManager.status
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = NetworkStatus.Unavailable
        )

    val currentTheme: StateFlow<AppTheme> = interactor.currentTheme
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AppTheme.FollowSystem
        )

    init {
        loadMovie()
    }

    override fun handleError(throwable: Throwable) {
        when (throwable) {
            is MovieDetailsException -> _detailsState.value = ScreenState.Failure(throwable)
            else -> super.handleError(throwable)
        }
    }

    fun retry() = loadMovie()

    fun onGenerateColors(movieId: Int, palette: Palette) = launch {
        val containerColor = palette.vibrantSwatch?.rgb
        val onContainerColor = palette.vibrantSwatch?.bodyTextColor
        if (containerColor != null && onContainerColor != null) {
            interactor.updateMovieColors(movieId, containerColor, onContainerColor)
            _detailsState.value = ScreenState.Content(interactor.movie(movieList.orEmpty(), movieId))
        }
    }

    private fun loadMovie() = launch {
        _detailsState.emit(ScreenState.Content(interactor.movieDetails(movieList.orEmpty(), movieId)))
    }
}