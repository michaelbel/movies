package org.michaelbel.moviemade.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Mark(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String
) : Serializable