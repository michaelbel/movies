package org.michaelbel.moviemade.app.data.model

import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: List<Cast>,
    @SerializedName("crew") val crew: List<Crew>
)