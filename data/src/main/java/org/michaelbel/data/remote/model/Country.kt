package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Country(
        @SerializedName("iso_3166_1") val country: String,
        @SerializedName("title") val name: String
): Serializable