package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Network(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("homepage") val homepage: String,
        @SerializedName("headquarters") val headquarters: String,
        @SerializedName("origin_country") val originCountry: String
): Serializable