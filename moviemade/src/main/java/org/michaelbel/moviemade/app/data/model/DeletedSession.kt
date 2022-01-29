package org.michaelbel.moviemade.app.data.model

import com.google.gson.annotations.SerializedName

data class DeletedSession(
    @SerializedName("success") val success: Boolean
)