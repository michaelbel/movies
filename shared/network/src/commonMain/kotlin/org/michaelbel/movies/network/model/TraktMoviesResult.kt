package org.michaelbel.movies.network.model

data class TraktMoviesResult(
    val page: Int,
    val totalPages: Int,
    val results: List<TraktMovieResponse>
)
