package org.michaelbel.moviemade.data.model

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres") val genres: List<Genre>
)