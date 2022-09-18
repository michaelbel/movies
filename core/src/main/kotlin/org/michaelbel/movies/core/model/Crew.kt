package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    @SerialName("credit_id") val creditId: String,
    @SerialName("department") val department: String,
    @SerialName("gender") val gender: Int,
    @SerialName("id") val id: Int,
    @SerialName("job") val job: String,
    @SerialName("name") val name: String,
    @SerialName("profile_path") val profilePath: String,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("overview") val overview: String,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("video") val video: Boolean,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("backdrop_path") val backdropPath: String,
    @SerialName("title") val title: String,
    @SerialName("popularity") val popularity: Float,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("vote_average") val voteAverage: Float,
    @SerialName("adult") val adult: Boolean?,
    @SerialName("release_date") val releaseDate: String
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