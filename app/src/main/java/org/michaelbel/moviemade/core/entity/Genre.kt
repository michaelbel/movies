package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
): Serializable