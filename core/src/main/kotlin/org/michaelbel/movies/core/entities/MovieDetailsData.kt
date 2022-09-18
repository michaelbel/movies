package org.michaelbel.movies.core.entities

data class MovieDetailsData(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Float
) {
    companion object {
        val EMPTY: MovieDetailsData = MovieDetailsData(
            id = 0,
            overview = "",
            posterPath = "",
            backdropPath = "",
            releaseDate = "",
            title = "",
            voteAverage = 0F
        )
    }
}