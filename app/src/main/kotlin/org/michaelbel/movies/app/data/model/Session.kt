package org.michaelbel.movies.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Session(
    @SerialName("success") val success: Boolean,
    @SerialName("session_id") val sessionId: String
)