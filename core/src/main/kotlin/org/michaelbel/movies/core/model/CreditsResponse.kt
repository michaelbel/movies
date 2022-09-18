package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponse(
    @SerialName("id") val id: Int,
    @SerialName("cast") val cast: List<Cast>,
    @SerialName("crew") val crew: List<Crew>
)