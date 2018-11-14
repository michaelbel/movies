package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Language(
    @SerializedName("iso_639_1") val language: String,
    @SerializedName("name") val name: String
) : Serializable