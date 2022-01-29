package org.michaelbel.moviemade.app.data.model

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("cast_id") val castId: Int,
    @SerializedName("character") val character: String,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("order") val order: Int,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("title") val title: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String
)