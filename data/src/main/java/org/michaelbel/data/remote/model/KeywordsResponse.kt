package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName
import org.michaelbel.data.Keyword
import java.io.Serializable

data class KeywordsResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("keywords") val keywords: List<Keyword>
): Serializable