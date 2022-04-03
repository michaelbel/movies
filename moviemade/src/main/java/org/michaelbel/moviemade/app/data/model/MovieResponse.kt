package org.michaelbel.moviemade.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("id") val id: Int,
    @SerialName("overview") val overview: String?,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("title") val title: String,
    @SerialName("vote_average") val voteAverage: Float,
    @SerialName("genre_ids") val genreIds: List<Int>
) {

    val backdropPathSafe: String
        get() = backdropPath ?: EMPTY_BACKDROP_PATH

    val overviewSafe: String
        get() = overview ?: EMPTY_OVERVIEW

    val genres: String
        get() = genreIds.take(2).joinToString(limit = 2, separator = ", ") {
            Genre.getGenreById(it)?.name.orEmpty()
        }

    private companion object {
        private const val EMPTY_BACKDROP_PATH = "https://null"
        private const val EMPTY_OVERVIEW = "No Overview"
    }
}