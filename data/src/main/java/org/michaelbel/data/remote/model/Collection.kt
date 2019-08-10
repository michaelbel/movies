package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Collection(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("name") val name: String? = null,
        @SerializedName("overview") val overview: String? = null,
        @SerializedName("poster_path") val posterPath: String? = null,
        @SerializedName("backdrop_path") val backdropPath: String? = null,
        @SerializedName("parts") val parts: List<Movie> = emptyList()
): Serializable