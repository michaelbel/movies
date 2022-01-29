package org.michaelbel.moviemade.app.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Calendar
import java.util.Date

data class MovieResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: Date,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("genre_ids") val genreIds: List<Int>
): Serializable {

    val backdropPathSafe: String
        get() = backdropPath ?: EMPTY_BACKDROP_PATH

    val overviewSafe: String
        get() = overview ?: EMPTY_OVERVIEW

    val releaseYear: String
        get() = Calendar.getInstance().apply { time = releaseDate }.get(Calendar.YEAR).toString()

    val genres: String
        get() = genreIds.take(2).joinToString(limit = 2, separator = ", ") {
            Genre.getGenreById(it)?.name.orEmpty()
        }

    private companion object {
        private const val EMPTY_BACKDROP_PATH = "https://null"
        private const val EMPTY_OVERVIEW = "No Overview"
    }
}