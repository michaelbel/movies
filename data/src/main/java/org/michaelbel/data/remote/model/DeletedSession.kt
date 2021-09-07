package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName

data class DeletedSession(
    @SerializedName("success") val success: Boolean
)