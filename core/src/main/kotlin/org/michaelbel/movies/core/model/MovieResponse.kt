package org.michaelbel.movies.core.model

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
        get() = backdropPath.orEmptyBackdropPath()

    val overviewSafe: String
        get() = overview.orEmptyOverview()

    val genres: String
        get() = genreIds.take(2).joinToString(limit = 2, separator = ", ") {
            Genre.getGenreById(it)?.name.orEmpty()
        }
}

fun String?.orEmptyBackdropPath(): String = this ?: "https://null"

fun String?.orEmptyOverview(): String = this ?: "No Overview"