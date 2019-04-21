package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName
import org.michaelbel.data.Review
import java.io.Serializable

data class ReviewsResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("page") val page: Int,
        @SerializedName("results") val reviews: List<Review>,
        @SerializedName("total_pages") val totalPages: Int,
        @SerializedName("total_results") val totalResults: Int
): Serializable