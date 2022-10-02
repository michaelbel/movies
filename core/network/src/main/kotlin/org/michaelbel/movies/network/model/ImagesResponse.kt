package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesResponse(
    @SerialName("id") val id: Int,
    @SerialName("backdrops") val cast: List<Image>,
    @SerialName("posters") val crew: List<Image>
)