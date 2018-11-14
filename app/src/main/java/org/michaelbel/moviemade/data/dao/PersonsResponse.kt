package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PersonsResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val people: List<Person>,
    @SerializedName("dates") val dates: Dates,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
) : Serializable