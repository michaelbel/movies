package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TraktUserResponse(
    @SerialName("username") val username: String,
    @SerialName("name") val name: String? = null,
    @SerialName("ids") val ids: TraktUserIdsResponse,
    @SerialName("images") val images: TraktUserImagesResponse? = null
)
