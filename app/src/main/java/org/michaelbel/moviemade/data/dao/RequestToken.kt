package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RequestToken(
    @SerializedName("request_token") val requestToken: String
) : Serializable