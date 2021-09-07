package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("success") val success: Boolean,
    @SerializedName("expires_at") val date: String,
    @SerializedName("request_token") val requestToken: String
)