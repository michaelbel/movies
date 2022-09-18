package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Account(
    @SerialName("avatar") val avatar: Avatar,
    @SerialName("id") val id: Int,
    @SerialName("iso_639_1") val lang: String,
    @SerialName("iso_3166_1") val country: String,
    @SerialName("name") val name: String,
    @SerialName("include_adult") val includeAdult: Boolean,
    @SerialName("username") val username: String
)