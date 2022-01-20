package org.michaelbel.moviemade.data.model

import com.google.gson.annotations.SerializedName

data class KeywordsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("keywords") val keywords: List<Keyword>
)