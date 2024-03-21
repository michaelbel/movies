@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@Entity(tableName = "movies", primaryKeys = ["movieList", "id"])
data class MovieDb(
    @NotNull val movieList: String,
    @NotNull val dateAdded: Long,
    @Nullable val page: Int?,
    @NotNull val position: Int,
    @NotNull @ColumnInfo(name = "id") val movieId: Int,
    @NotNull val overview: String,
    @NotNull val posterPath: String,
    @NotNull val backdropPath: String,
    @NotNull val releaseDate: String,
    @NotNull val title: String,
    @NotNull val voteAverage: Float,
    @Nullable val containerColor: Int?,
    @Nullable val onContainerColor: Int?
) {
    companion object {
        const val MOVIES_LOCAL_LIST = "movies_local"
        const val MOVIES_SEARCH_HISTORY = "movies_search_history"
        const val MOVIES_WIDGET = "movies_widget"

        val Empty = MovieDb(
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