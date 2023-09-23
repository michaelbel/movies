package org.michaelbel.movies.details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.ktx.require
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.entities.lce.ScreenState
import org.michaelbel.movies.interactor.usecase.MovieDetailsCase
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieDetails: MovieDetailsCase,
    networkManager: NetworkManager
): BaseViewModel() {

    private val movieId: Int = savedStateHandle.require("movieId")

    private val _detailsState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading)
    val detailsState: StateFlow<ScreenState> = _detailsState.asStateFlow()

    val networkStatus: StateFlow<NetworkStatus> = networkManager.status
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = NetworkStatus.Unavailable
        )

    init {
        loadMovie()
    }

    fun retry() = loadMovie()

    private fun loadMovie() = launch {
        _detailsState.tryEmit(movieDetails(movieId))
    }
}