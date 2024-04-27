package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.persistence.database.typealiases.Position

@Entity(tableName = "movies", primaryKeys = ["movieList", "movieId"])
internal data class MovieDb(
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
)