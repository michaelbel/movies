package org.michaelbel.moviemade.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @SerialName("id") val id: String,
    @SerialName("iso_639_1") val lang: String,
    @SerialName("iso_3166_1") val country: String,
    @SerialName("key") val key: String,
    @SerialName("name") val name: String,
    @SerialName("site") val site: String,
    @SerialName("size") val size: Int,
    @SerialName("type") val type: String
) {

    companion object {
        const val SIZE_360 = 360
        const val SIZE_480 = 480
        const val SIZE_720 = 720
        const val SIZE_1080 = 1080

        const val TRAILER = "Trailer"
        const val TEASER = "Teaser"
        const val CLIP = "Clip"
        const val FEATURETTE = "Featurette"

        const val SITE_YOUTUBE = "YouTube"
    }
}