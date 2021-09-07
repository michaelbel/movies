package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName

data class GuestSession(
    @SerializedName("success") val success: Boolean,
    @SerializedName("guest_session_id") val guestSessionId: String,
    @SerializedName("expires_at") val date: String
)