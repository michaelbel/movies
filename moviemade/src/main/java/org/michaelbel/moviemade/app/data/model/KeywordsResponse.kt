package org.michaelbel.moviemade.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeywordsResponse(
    @SerialName("id") val id: Int,
    @SerialName("keywords") val keywords: List<Keyword>
)