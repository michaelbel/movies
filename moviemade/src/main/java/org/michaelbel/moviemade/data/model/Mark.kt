package org.michaelbel.moviemade.data.model

import com.google.gson.annotations.SerializedName

data class Mark(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String
) {

    companion object {
        const val ADDED = 1
        const val DELETED = 13
        const val UPDATED = 12
    }
}