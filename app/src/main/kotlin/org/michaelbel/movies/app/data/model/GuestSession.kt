package org.michaelbel.movies.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuestSession(
    @SerialName("success") val success: Boolean,
    @SerialName("guest_session_id") val guestSessionId: String,
    @SerialName("expires_at") val date: String
)