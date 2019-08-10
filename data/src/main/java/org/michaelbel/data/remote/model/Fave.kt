package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Fave(
        @SerializedName("media_type") val mediaType: String,
        @SerializedName("media_id") val mediaId: Long,
        @SerializedName("favorite") val favorite: Boolean
): Serializable