package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RequestToken(
    @SerializedName("request_token") val requestToken: String
): Serializable