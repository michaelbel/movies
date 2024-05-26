package org.michaelbel.movies.details

import androidx.lifecycle.SavedStateHandle
import org.michaelbel.movies.common.ktx.require
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class DetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val interactor: Interactor
): BaseViewModel() {

    private val movieList: PagingKey? = savedStateHandle["movieList"]
    private val movieId: MovieId = savedStateHandle.require("movieId")
}