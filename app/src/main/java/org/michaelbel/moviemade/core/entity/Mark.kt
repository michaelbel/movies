package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Mark(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String
) : Serializable