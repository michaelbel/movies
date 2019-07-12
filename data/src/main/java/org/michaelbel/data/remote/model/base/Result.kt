package org.michaelbel.data.remote.model.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.michaelbel.data.remote.model.Dates
import java.io.Serializable

open class Result<T>: Serializable {
    @Expose @SerializedName("id") val id: Int = 0
    @Expose @SerializedName("page") val page: Int = 0
    @Expose @SerializedName("results") val results: List<T> = emptyList()
    @Expose @SerializedName("total_pages") val totalPages: Int = 0
    @Expose @SerializedName("total_results") val totalResults: Int = 0
    @Expose @SerializedName("dates") val dates: Dates? = null
}