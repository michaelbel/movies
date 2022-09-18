package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Keyword(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String?
)