package org.michaelbel.movies.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Language(
    @SerialName("iso_639_1") val language: String,
    @SerialName("name") val name: String
)