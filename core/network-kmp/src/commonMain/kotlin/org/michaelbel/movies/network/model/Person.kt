package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    @SerialName("birthday") val birthDay: String,
    @SerialName("known_for_department") val knownForDepartment: String,
    @SerialName("deathday") val deathDay: String,
    @SerialName("id") val id: Int,
    @SerialName("title") val name: String,
    @SerialName("also_known_as") val alsoKnownAs: List<String>,
    @SerialName("gender") val gender: Int,
    @SerialName("biography") val biography: String,
    @SerialName("popularity") val popularity: Float,
    @SerialName("place_of_birth") val placeOfBirth: String,
    @SerialName("profile_path") val profilePath: String,
    @SerialName("adult") val adult: Boolean?,
    @SerialName("imdb_id") val imdbId: String,
    @SerialName("homepage") val homepage: String,
    @SerialName("known_for") val knownFor: List<Movie>
)