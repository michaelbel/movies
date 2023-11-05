package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesResponse(
    @SerialName("id") val id: Int,
    @SerialName("backdrops") val backdrops: List<Image>,
    @SerialName("posters") val posters: List<Image>,
    @SerialName("logos") val logos: List<Image>
)