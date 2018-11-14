package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImagesResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("backdrops") val cast: List<Image>,
    @SerializedName("posters") val crew: List<Image>
) : Serializable