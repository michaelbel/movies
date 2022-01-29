package org.michaelbel.moviemade.app.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Result<T>: Serializable {
    @SerializedName("id") val id: Int = 0
    @SerializedName("page") val page: Int = 0
    @SerializedName("results") val results: List<T> = emptyList()
    @SerializedName("total_pages") val totalPages: Int = 0
    @SerializedName("total_results") val totalResults: Int = 0
    @SerializedName("dates") val dates: Dates? = null
}