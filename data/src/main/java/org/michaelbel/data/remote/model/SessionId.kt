package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName

data class SessionId(
    @SerializedName("session_id") val sessionId: String
)