package org.michaelbel.moviemade.app.data.model

import com.google.gson.annotations.SerializedName

data class Watch(
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("media_id") val mediaId: Long,
    @SerializedName("watchlist") val watchlist: Boolean
)