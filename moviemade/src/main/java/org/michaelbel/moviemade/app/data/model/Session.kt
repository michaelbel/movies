package org.michaelbel.moviemade.app.data.model

import com.google.gson.annotations.SerializedName

data class Session(
    @SerializedName("success") val success: Boolean,
    @SerializedName("session_id") val sessionId: String
)