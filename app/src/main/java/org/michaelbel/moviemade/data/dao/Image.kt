package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Poster or Backdrop
data class Image(
    @SerializedName("aspect_ratio") val aspectRatio: Float,
    @SerializedName("file_path") val filePath: String,
    @SerializedName("height") val height: Int,
    @SerializedName("iso_639_1") val lang: String,
    @SerializedName("vote_average") val voteAverage: Int,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("width") val width: Int
) : Serializable