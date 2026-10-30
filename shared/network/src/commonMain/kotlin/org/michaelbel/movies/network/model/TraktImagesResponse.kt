package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TraktImagesResponse(
    @SerialName("poster") val poster: List<String> = emptyList(),
    @SerialName("fanart") val fanart: List<String> = emptyList()
)
