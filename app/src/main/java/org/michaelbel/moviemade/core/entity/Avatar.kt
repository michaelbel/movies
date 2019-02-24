package org.michaelbel.moviemade.core.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Avatar(
    @SerializedName("gravatar") val gravatar: GrAvatar
) : Serializable