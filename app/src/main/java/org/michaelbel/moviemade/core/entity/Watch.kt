package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Watch(
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("media_id") val mediaId: Int,
    @SerializedName("watchlist") val watchlist: Boolean
): Serializable