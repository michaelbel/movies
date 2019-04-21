package org.michaelbel.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Account(
        @Expose @SerializedName("avatar") val avatar: Avatar,
        @Expose @SerializedName("id") val id: Int,
        @Expose @SerializedName("iso_639_1") val lang: String,
        @Expose @SerializedName("iso_3166_1") val country: String,
        @Expose @SerializedName("name") val name: String,
        @Expose @SerializedName("include_adult") val include_adult: Boolean,
        @Expose @SerializedName("username") val username: String
): Serializable