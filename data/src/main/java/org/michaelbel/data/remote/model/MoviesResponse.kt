package org.michaelbel.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.michaelbel.data.Movie
import java.io.Serializable

data class MoviesResponse(
        @Expose @SerializedName("page") val page: Int,
        @Expose @SerializedName("results") val movies: List<Movie>,
        @Expose @SerializedName("dates") val dates: Dates,
        @Expose @SerializedName("total_pages") val totalPages: Int,
        @Expose @SerializedName("total_results") val totalResults: Int
): Serializable {

    companion object {
        const val ASC = "created_at.asc"
        const val DESC = "created_at.desc"

        const val NOW_PLAYING = "now_playing"
        const val POPULAR = "popular"
        const val TOP_RATED = "top_rated"
        const val UPCOMING = "upcoming"

        const val SIMILAR = "similar"
        const val RECOMMENDATIONS = "recommendations"

        const val FAVORITE = "favorite"
        const val WATCHLIST = "watchlist"
    }
}