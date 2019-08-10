package org.michaelbel.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Keyword(
        @Expose @SerializedName("id") val id: Long = 0L,
        @Expose @SerializedName("name") val name: String? = null
): Serializable