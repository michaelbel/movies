package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Cast(
        @Expose @SerializedName("cast_id") val castId: Int,
        @Expose @SerializedName("character") val character: String,
        @Expose @SerializedName("credit_id") val creditId: String,
        @Expose @SerializedName("gender") val gender: Int,
        @Expose @SerializedName("id") val id: Int,
        @Expose @SerializedName("name") val name: String,
        @Expose @SerializedName("order") val order: Int,
        @Expose @SerializedName("profile_path") val profilePath: String,
        @Expose @SerializedName("vote_count") val voteCount: Int,
        @Expose @SerializedName("video") val video: Boolean,
        @Expose @SerializedName("vote_average") val voteAverage: Float,
        @Expose @SerializedName("title") val title: String,
        @Expose @SerializedName("genre_ids") val genreIds: List<Int>,
        @Expose @SerializedName("original_language") val originalLanguage: String,
        @Expose @SerializedName("original_title") val originalTitle: String,
        @Expose @SerializedName("popularity") val popularity: Float,
        @Expose @SerializedName("backdrop_path") val backdropPath: String,
        @Expose @SerializedName("overview") val overview: String,
        @Expose @SerializedName("poster_path") val posterPath: String
): Serializable