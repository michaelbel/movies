package org.michaelbel.movies.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Token(
    @SerialName("success") val success: Boolean,
    @SerialName("expires_at") val date: String,
    @SerialName("request_token") val requestToken: String
)