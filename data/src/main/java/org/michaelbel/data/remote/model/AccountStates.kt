package org.michaelbel.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AccountStates(
        @Expose @SerializedName("id") val id: Int,
        @Expose @SerializedName("favorite") val favorite: Boolean,
        @Expose @SerializedName("watchlist") val watchlist: Boolean
        //@Expose @SerializedName("rated") val rated: Rated
): Serializable