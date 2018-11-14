package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Country(
    @SerializedName("iso_3166_1") val country: String,
    @SerializedName("name") val name: String
) : Serializable