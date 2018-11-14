package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CollectionsResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val collections: List<Collection>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
) : Serializable