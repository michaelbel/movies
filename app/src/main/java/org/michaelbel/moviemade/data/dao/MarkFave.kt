package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MarkFave(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String
) : Serializable