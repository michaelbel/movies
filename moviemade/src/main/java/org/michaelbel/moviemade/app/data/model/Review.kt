package org.michaelbel.moviemade.app.data.model

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("id") val id: String = "",
    @SerializedName("author") val author: String = "",
    @SerializedName("content") val content: String = "",
    @SerializedName("iso_639_1") val lang: String = "",
    @SerializedName("media_id") val mediaId: Int = 0,
    @SerializedName("media_title") val mediaTitle: String = "",
    @SerializedName("media_type") val mediaType: String = "",
    @SerializedName("url") val url: String = ""
)