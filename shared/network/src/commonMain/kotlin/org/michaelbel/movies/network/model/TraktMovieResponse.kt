package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TraktMovieResponse(
    @SerialName("title") val title: String?,
    @SerialName("year") val year: Int?,
    @SerialName("overview") val overview: String?,
    @SerialName("released") val released: String?,
    @SerialName("rating") val rating: Float?,
    @SerialName("ids") val ids: TraktIdsResponse,
    @SerialName("images") val images: TraktImagesResponse? = null
)
