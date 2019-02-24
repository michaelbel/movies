package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class KeywordsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("keywords") val keywords: List<Keyword>
) : Serializable