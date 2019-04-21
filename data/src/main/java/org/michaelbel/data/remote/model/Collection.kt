package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName
import org.michaelbel.data.Movie
import java.io.Serializable

data class Collection(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("overview") val overview: String,
        @SerializedName("poster_path") val posterPath: String,
        @SerializedName("backdrop_path") val backdropPath: String,
        @SerializedName("parts") val parts: List<Movie>
): Serializable {

    constructor(): this(
            id = 0,
            name = "",
            overview = "",
            posterPath = "",
            backdropPath = "",
            parts = emptyList()
    )
}