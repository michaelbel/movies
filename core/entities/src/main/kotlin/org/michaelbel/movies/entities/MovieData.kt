package org.michaelbel.movies.entities

data class MovieData(
    val id: Int = 0,
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val voteAverage: Float = 0F,
    val genreIds: List<Int> = listOf()
) {
    companion object {
        const val NOW_PLAYING = "now_playing"
        const val DEFAULT_PAGE_SIZE = 10
    }
}