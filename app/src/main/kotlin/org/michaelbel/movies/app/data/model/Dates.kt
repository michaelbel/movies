package org.michaelbel.movies.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dates(
    @SerialName("minimum") val minimumDate: String,
    @SerialName("maximum") val maximumDate: String
)