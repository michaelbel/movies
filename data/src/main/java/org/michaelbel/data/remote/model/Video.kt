package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("id") val id: String = "",
    @SerializedName("iso_639_1") val lang: String = "",
    @SerializedName("iso_3166_1") val country: String = "",
    @SerializedName("key") val key: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("site") val site: String = "",
    @SerializedName("size") val size: Int = 0,
    @SerializedName("type") val type: String = "",
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