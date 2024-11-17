package org.michaelbel.movies.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.ktx.require
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.config.ScreenState
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class DetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val interactor: Interactor
): BaseViewModel() {

    private val movieList: PagingKey? = savedStateHandle["movieList"]
    private val movieId: MovieId = savedStateHandle.require("movieId")

    private val _detailsState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val detailsState: StateFlow<ScreenState> get() = _detailsState.asStateFlow()

    init {
        loadMovie()
    }

    fun retry() = loadMovie()

    private fun loadMovie() = viewModelScope.launch {
        val movieDb = interactor.movieDetails(movieList.orEmpty(), movieId)
        _detailsState.value = ScreenState.Content(movieDb)
    }
}