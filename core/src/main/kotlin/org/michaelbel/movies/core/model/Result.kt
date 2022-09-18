package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class Result<T> {
    @SerialName("id") val id: Int = 0
    @SerialName("page") val page: Int = 0
    @SerialName("results") val results: List<T> = emptyList()
    @SerialName("total_pages") val totalPages: Int = 0
    @SerialName("total_results") val totalResults: Int = 0
    @SerialName("dates") val dates: Dates? = null
}