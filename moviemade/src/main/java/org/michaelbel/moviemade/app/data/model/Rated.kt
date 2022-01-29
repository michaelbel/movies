package org.michaelbel.moviemade.app.data.model

import com.google.gson.annotations.SerializedName

data class Rated(
    @SerializedName("value") val value: Int
)