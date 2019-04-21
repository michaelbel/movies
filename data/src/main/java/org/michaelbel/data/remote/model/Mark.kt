package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Mark(
        @SerializedName("status_code") val statusCode: Int,
        @SerializedName("status_message") val statusMessage: String
): Serializable {

    companion object {
        const val ADDED = 1
        const val DELETED = 13
        const val UPDATED = 12
    }
}