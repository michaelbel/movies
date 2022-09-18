package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    @SerialName("cast_id") val castId: Int,
    @SerialName("character") val character: String,
    @SerialName("credit_id") val creditId: String,
    @SerialName("gender") val gender: Int,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("order") val order: Int,
    @SerialName("profile_path") val profilePath: String,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("video") val video: Boolean,
    @SerialName("vote_average") val voteAverage: Float,
    @SerialName("title") val title: String,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("popularity") val popularity: Float,
    @SerialName("backdrop_path") val backdropPath: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String
)