package org.michaelbel.movies.details.model

import org.michaelbel.movies.entities.MovieDetailsData

sealed interface DetailsState {
    object Loading: DetailsState
    data class Content(val movie: MovieDetailsData): DetailsState
    data class Failure(val throwable: Throwable): DetailsState
}