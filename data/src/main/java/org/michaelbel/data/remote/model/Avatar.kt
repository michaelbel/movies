package org.michaelbel.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Avatar(
        @Expose @SerializedName("gravatar") val gravatar: GrAvatar
): Serializable