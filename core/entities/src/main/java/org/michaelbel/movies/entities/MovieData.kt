package org.michaelbel.movies.entities

data class MovieData(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Float,
    val genreIds: List<Int>
)