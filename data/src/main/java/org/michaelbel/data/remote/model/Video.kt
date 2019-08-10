package org.michaelbel.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Video(
        @Expose @SerializedName("id") val id: String = "",
        @Expose @SerializedName("iso_639_1") val lang: String = "",
        @Expose @SerializedName("iso_3166_1") val country: String = "",
        @Expose @SerializedName("key") val key: String = "",
        @Expose @SerializedName("name") val name: String = "",
        @Expose @SerializedName("site") val site: String = "",
        @Expose @SerializedName("size") val size: Int = 0,
        @Expose @SerializedName("type") val type: String = "",

        val movieId: Int = 0
): Serializable {

    companion object {
        const val SIZE_360 = 360
        const val SIZE_480 = 480
        const val SIZE_720 = 720
        const val SIZE_1080 = 1080

        const val TRAILER = "Trailer"
        const val TEASER = "Teaser"
        const val CLIP = "Clip"
        const val FEATURETTE = "Featurette"
    }
}