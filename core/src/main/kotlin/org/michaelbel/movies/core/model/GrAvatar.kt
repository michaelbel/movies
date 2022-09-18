package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GrAvatar(
    @SerialName("hash") val hash: String
)