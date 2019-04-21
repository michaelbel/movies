package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rated(
        @SerializedName("value") val value: Int
): Serializable