package org.michaelbel.movies.domain.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDb(
    val movieList: String,
    val dateAdded: Long,
    val position: Int,
    @PrimaryKey @ColumnInfo(name = "id") val movieId: Int,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Float
) {
    companion object {
        val Empty: MovieDb = MovieDb(
            movieList = "",
            dateAdded = 0L,
            position = 0,
            movieId = 0,
            overview = "",
            posterPath = "",
            backdropPath = "",
            releaseDate = "",
            title = "",
            voteAverage = 0F
        )
    }
}