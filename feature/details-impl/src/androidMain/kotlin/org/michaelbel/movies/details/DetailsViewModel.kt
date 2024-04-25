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

class DetailsViewModel(
    savedStateHandle: SavedStateHandle,
    networkManager: NetworkManager,
    private val interactor: Interactor
): BaseViewModel() {

    private val movieList: String? = savedStateHandle["movieList"]
    private val movieId: Int = savedStateHandle.require("movieId")

    private val _detailsState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val detailsState = _detailsState.asStateFlow()

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
            if (movieList != null) {
                _detailsState.value = ScreenState.Content(interactor.movie(movieList, movieId))
            }
        }
    }

    private fun loadMovie() = launch {
        val movieDb = interactor.movieDetails(movieList.orEmpty(), movieId)
        _detailsState.value = ScreenState.Content(movieDb)
    }
}