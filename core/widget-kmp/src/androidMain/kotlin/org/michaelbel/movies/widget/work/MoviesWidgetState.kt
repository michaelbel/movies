package org.michaelbel.movies.widget.work

import kotlinx.serialization.Serializable
import org.michaelbel.movies.widget.entity.MovieData

@Serializable
internal sealed interface MoviesWidgetState {

    @Serializable
    data object Loading: MoviesWidgetState

    @Serializable
    data class Content(
        val movies: List<MovieData>
    ): MoviesWidgetState

    @Serializable
    data class Failure(
        val message: String
    ): MoviesWidgetState
}