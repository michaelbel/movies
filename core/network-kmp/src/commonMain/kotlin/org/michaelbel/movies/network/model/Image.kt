package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("height") val height: Int,
    @SerialName("width") val width: Int,
    @SerialName("aspect_ratio") val aspectRatio: Float,
    @SerialName("iso_639_1") val lang: String?,
    @SerialName("file_path") val filePath: String,
    @SerialName("vote_average") val voteAverage: Float,
    @SerialName("vote_count") val voteCount: Int
)