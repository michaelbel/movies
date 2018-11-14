package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CompaniesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val companies: List<Company>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
) : Serializable