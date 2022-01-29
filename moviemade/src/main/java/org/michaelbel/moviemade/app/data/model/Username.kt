package org.michaelbel.moviemade.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Username(
    @SerialName("username") val username: String? = null,
    @SerialName("password") val password: String? = null,
    @SerialName("request_token") val requestToken: String? = null
)