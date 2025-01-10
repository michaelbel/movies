package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionRequest(
    @SerialName("session_id") val sessionId: String
)