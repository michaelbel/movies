package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideosResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val trailers: List<Video>
) : Serializable