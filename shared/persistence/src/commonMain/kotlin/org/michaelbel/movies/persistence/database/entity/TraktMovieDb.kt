package org.michaelbel.movies.persistence.database.entity

import androidx.room3.Entity
import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.persistence.database.typealiases.Position

@Entity(
    tableName = "trakt_movies",
    primaryKeys = ["traktList", "traktId"]
)
data class TraktMovieDb(
    val traktList: PagingKey,
    val dateAdded: Long,
    val page: Page?,
    val position: Position,
    val traktId: Int,
    val tmdbId: Int?,
    val overview: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Float
)
