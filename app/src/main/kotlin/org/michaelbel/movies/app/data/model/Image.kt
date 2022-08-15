package org.michaelbel.movies.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Poster or Backdrop.
 */
@Serializable
data class Image(
    @SerialName("aspect_ratio") val aspectRatio: Float,
    @SerialName("file_path") val filePath: String,
    @SerialName("height") val height: Int,
    @SerialName("iso_639_1") val lang: String,
    @SerialName("vote_average") val voteAverage: Int,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("width") val width: Int
)