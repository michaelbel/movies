package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Suppress("unused")
data class MoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("dates") val dates: Dates,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
): Serializable {

    companion object {
        const val ASC = "created_at.asc"
        const val DESC = "created_at.desc"
    }
}