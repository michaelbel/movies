package org.michaelbel.movies.core.entities

import org.michaelbel.movies.core.model.Genre

data class MovieData(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Float,
    val genreIds: List<Int>
) {
    val genres: String
        get() = genreIds.take(2).joinToString(limit = 2, separator = ", ") {
            Genre.getGenreById(it)?.name.orEmpty()
        }
}