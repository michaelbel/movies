package org.michaelbel.moviemade.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Fave(
    @SerialName("media_type") val mediaType: String,
    @SerialName("media_id") val mediaId: Long,
    @SerialName("favorite") val favorite: Boolean
)