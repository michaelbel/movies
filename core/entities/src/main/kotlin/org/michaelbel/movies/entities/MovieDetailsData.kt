package org.michaelbel.movies.entities

data class MovieDetailsData(
    val id: Int = 0,
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val voteAverage: Float = 0F
)