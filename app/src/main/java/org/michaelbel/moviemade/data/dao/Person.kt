package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Person(
    @SerializedName("birthday") val birthDay: String,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("deathday") val deathDay: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("also_known_as") val also_known_as: List<String>,
    @SerializedName("gender") val gender: Int,
    @SerializedName("biography") val biography: String,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("place_of_birth") val placeOfBirth: String,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("imdb_id") val imdb_id: String,
    @SerializedName("homepage") val homepage: String,

    @SerializedName("known_for") val knownFor: List<Movie>
) : Serializable