package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Avatar(
    @SerialName("gravatar") val gravatar: GrAvatar
)