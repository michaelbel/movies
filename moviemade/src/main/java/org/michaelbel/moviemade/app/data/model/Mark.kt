package org.michaelbel.moviemade.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mark(
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message") val statusMessage: String
) {

    companion object {
        const val ADDED = 1
        const val DELETED = 13
        const val UPDATED = 12
    }
}