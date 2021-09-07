package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName

data class Rated(
    @SerializedName("value") val value: Int
)