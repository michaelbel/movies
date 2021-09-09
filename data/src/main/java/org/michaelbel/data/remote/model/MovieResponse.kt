package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class MovieResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("release_date") val releaseDate: Date,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("genre_ids") val genreIds: List<Int>
): Serializable {

    val releaseYear: String
        get() = Calendar.getInstance().apply { time = releaseDate }.get(Calendar.YEAR).toString()

    val genres: String
        get() = genreIds.take(2).joinToString(limit = 2, separator = ", ") { Genre.getGenreById(it)?.name.orEmpty() }
}