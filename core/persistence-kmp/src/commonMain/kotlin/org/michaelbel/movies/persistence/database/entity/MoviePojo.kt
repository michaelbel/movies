package org.michaelbel.movies.persistence.database.entity

data class MoviePojo(
    val movieList: String,
    val dateAdded: Long,
    val page: Int?,
    val position: Int,
    val movieId: Int,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Float,
    val containerColor: Int?,
    val onContainerColor: Int?
) {
    companion object {
        const val MOVIES_LOCAL_LIST = "movies_local"
        const val MOVIES_SEARCH_HISTORY = "movies_search_history"
        const val MOVIES_WIDGET = "movies_widget"

        val Empty = MoviePojo(
            movieList = "",
            dateAdded = 0L,
            page = null,
            position = 0,
            movieId = 0,
            overview = "",
            posterPath = "",
            backdropPath = "",
            releaseDate = "",
            title = "",
            voteAverage = 0F,
            containerColor = null,
            onContainerColor = null
        )
    }
}