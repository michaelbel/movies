package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import org.michaelbel.moviemade.rest.model.Language
import org.michaelbel.moviemade.rest.model.v3.*
import org.michaelbel.moviemade.rest.model.v3.Collection
import java.io.Serializable

data class VideosResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val trailers: List<Video>
) : Serializable