package org.michaelbel.moviemade.app.data.model

import com.google.gson.annotations.SerializedName

data class Network(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("headquarters") val headquarters: String,
    @SerializedName("origin_country") val originCountry: String
)