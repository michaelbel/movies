package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName

data class Dates(
    @SerializedName("minimum") val minimumDate: String,
    @SerializedName("maximum") val maximumDate: String
)