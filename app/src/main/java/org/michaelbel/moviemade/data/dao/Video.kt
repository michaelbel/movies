package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import org.michaelbel.moviemade.rest.model.Language
import org.michaelbel.moviemade.rest.model.v3.*
import org.michaelbel.moviemade.rest.model.v3.Collection
import java.io.Serializable

data class Video(
    @SerializedName("id") val id: Int,
    @SerializedName("iso_639_1") val lang: String,
    @SerializedName("iso_3166_1") val country: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("site") val site: String,
    @SerializedName("size") val size: Int,
    @SerializedName("type") val type: String
) : Serializable