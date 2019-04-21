package org.michaelbel.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Network(
        @Expose @SerializedName("id") val id: Int,
        @Expose @SerializedName("name") val name: String,
        @Expose @SerializedName("homepage") val homepage: String,
        @Expose @SerializedName("headquarters") val headquarters: String,
        @Expose @SerializedName("origin_country") val originCountry: String
): Serializable