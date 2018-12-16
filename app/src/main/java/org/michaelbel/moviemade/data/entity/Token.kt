package org.michaelbel.moviemade.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Token(
    @SerializedName("success") val success: Boolean,
    @SerializedName("expires_at") val date: String,
    @SerializedName("request_token") val requestToken: String
) : Serializable