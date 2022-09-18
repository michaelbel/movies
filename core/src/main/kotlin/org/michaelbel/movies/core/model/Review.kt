package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Review(
    @SerialName("id") val id: String,
    @SerialName("author") val author: String,
    @SerialName("content") val content: String,
    @SerialName("iso_639_1") val lang: String,
    @SerialName("media_id") val mediaId: Int,
    @SerialName("media_title") val mediaTitle: String,
    @SerialName("media_type") val mediaType: String,
    @SerialName("url") val url: String
)