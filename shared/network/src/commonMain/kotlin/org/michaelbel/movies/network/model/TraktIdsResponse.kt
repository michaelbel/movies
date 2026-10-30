package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TraktIdsResponse(
    @SerialName("trakt") val trakt: Int,
    @SerialName("slug") val slug: String?,
    @SerialName("imdb") val imdb: String?,
    @SerialName("tmdb") val tmdb: Int?
)
