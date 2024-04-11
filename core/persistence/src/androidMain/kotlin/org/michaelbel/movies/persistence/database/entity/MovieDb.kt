package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity

@Entity(tableName = "movies", primaryKeys = ["movieList", "movieId"])
internal data class MovieDb(
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
)