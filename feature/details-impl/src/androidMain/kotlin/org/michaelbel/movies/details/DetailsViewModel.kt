package org.michaelbel.movies.details

import androidx.lifecycle.SavedStateHandle
import androidx.palette.graphics.Palette
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.exceptions.MovieDetailsException
import org.michaelbel.movies.common.ktx.require
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.config.ScreenState
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class DetailsViewModel(
    savedStateHandle: SavedStateHandle,
    networkManager: NetworkManager,
    private val interactor: Interactor
): BaseViewModel() {

    private val movieList: PagingKey? = savedStateHandle["movieList"]
    private val movieId: MovieId = savedStateHandle.require("movieId")

    private val _detailsState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val detailsState: StateFlow<ScreenState> get() = _detailsState.asStateFlow()

    val networkStatus: StateFlow<NetworkStatus> = networkManager.status
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = NetworkStatus.Unavailable
        )

    val currentTheme: StateFlow<AppTheme> = interactor.currentTheme
        .stateIn(
            scope = scope,
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

    fun onGenerateColors(movieId: MovieId, palette: Palette) = scope.launch {
        val containerColor = palette.vibrantSwatch?.rgb
        val onContainerColor = palette.vibrantSwatch?.bodyTextColor
        if (containerColor != null && onContainerColor != null) {
            interactor.updateMovieColors(movieId, containerColor, onContainerColor)
            if (movieList != null) {
                _detailsState.value = ScreenState.Content(interactor.movie(movieList, movieId))
            }
        }
    }

    private fun loadMovie() = scope.launch {
        val movieDb = interactor.movieDetails(movieList.orEmpty(), movieId)
        _detailsState.value = ScreenState.Content(movieDb)
    }
}