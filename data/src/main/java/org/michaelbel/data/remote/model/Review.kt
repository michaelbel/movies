package org.michaelbel.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Review(
        @Expose @SerializedName("id") val id: String = "",
        @Expose @SerializedName("author") val author: String = "",
        @Expose @SerializedName("content") val content: String = "",
        @Expose @SerializedName("iso_639_1") val lang: String = "",
        @Expose @SerializedName("media_id") val mediaId: Int = 0,
        @Expose @SerializedName("media_title") val mediaTitle: String = "",
        @Expose @SerializedName("media_type") val mediaType: String = "",
        @Expose @SerializedName("url") val url: String = "",

        val movieId: Int = 0
): Serializable