package org.michaelbel.moviemade.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Review(
    @SerializedName("id") val id: String,
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String,
    @SerializedName("iso_639_1") val lang: String,
    @SerializedName("media_id") val mediaId: Int,
    @SerializedName("media_title") val mediaTitle: String,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("url") val url: String
) : Serializable