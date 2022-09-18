package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeywordsResponse(
    @SerialName("id") val id: Int,
    @SerialName("keywords") val keywords: List<Keyword>
)