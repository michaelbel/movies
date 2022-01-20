package org.michaelbel.moviemade.data.model

import com.google.gson.annotations.SerializedName

data class Username(
    @SerializedName("username") val username: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("request_token") val requestToken: String? = null
)