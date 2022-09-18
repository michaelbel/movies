package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestToken(
    @SerialName("request_token") val requestToken: String
)