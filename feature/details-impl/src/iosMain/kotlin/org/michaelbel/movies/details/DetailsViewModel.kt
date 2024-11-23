package org.michaelbel.movies.details

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
    private val movieId: MovieId = savedStateHandle["movieId"] ?: 0

    private val _detailsState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val detailsState: StateFlow<ScreenState> get() = _detailsState.asStateFlow()

    init {
        println("movieList=$movieList, movieId=$movieId")
        loadMovie()
    }

    fun retry() = loadMovie()

    private fun loadMovie() = scope.launch {
        val movieDb = interactor.movieDetails(movieList.orEmpty(), movieId)
        _detailsState.value = ScreenState.Content(movieDb)
    }
}