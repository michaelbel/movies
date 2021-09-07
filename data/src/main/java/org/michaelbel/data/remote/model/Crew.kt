package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("department") val department: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("job") val job: String,
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("title") val title: String,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("release_date") val releaseDate: String
) {

    companion object {
        const val WRITING = "Writing"
        const val PRODUCTION = "Production"
        const val SOUND = "Sound"
        const val CAMERA = "Camera"
        const val EDITING = "Editing"
        const val ART = "Art"
        const val DIRECTING = "Directing"
        const val CREW = "Crew"
        const val LIGHTING = "Lighting"
        const val COSTUME_MAKEUP = "Costume & Make-Up"
        const val VISUAL_EFFECTS = "Visual Effects"
    }
}