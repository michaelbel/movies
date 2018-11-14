package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GrAvatar(
    @SerializedName("hash") val hash: String
) : Serializable