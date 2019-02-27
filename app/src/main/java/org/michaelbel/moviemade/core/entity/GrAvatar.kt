package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GrAvatar(
    @SerializedName("hash") val hash: String
): Serializable