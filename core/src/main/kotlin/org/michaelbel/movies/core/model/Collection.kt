package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Collection(
    @SerialName("id") val id: Int = 0,
    @SerialName("name") val name: String?,
    @SerialName("overview") val overview: String?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("parts") val parts: List<Movie>
)