package org.michaelbel.movies.domain.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "movies")
data class MovieDb(
    @NotNull val movieList: String,
    @NotNull val dateAdded: Long,
    @NotNull val position: Int,
    @NotNull @PrimaryKey @ColumnInfo(name = "id") val movieId: Int,
    @NotNull val overview: String,
    @NotNull val posterPath: String,
    @NotNull val backdropPath: String,
    @NotNull val releaseDate: String,
    @NotNull val title: String,
    @NotNull val voteAverage: Float
) {
    companion object {
        const val MOVIES_LOCAL_LIST = "movies_local"

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