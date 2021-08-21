package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DeletedSession(@SerializedName("success") val success: Boolean): Serializable