package org.michaelbel.movies.persistence.database.entity

import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.persistence.database.typealiases.Position

data class MoviePojo(
    val movieList: PagingKey,
    val dateAdded: Long,
    val page: Page?,
    val position: Position,
    val movieId: MovieId,
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