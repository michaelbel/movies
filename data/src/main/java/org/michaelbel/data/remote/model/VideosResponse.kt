package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName
import org.michaelbel.data.Video
import java.io.Serializable

data class VideosResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("results") val trailers: List<Video>
): Serializable