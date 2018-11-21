package org.michaelbel.moviemade.data.dao

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AccountStates(
    @SerializedName("id") val id: Int,
    @SerializedName("favorite") val favorite: Boolean,
    @SerializedName("watchlist") val watchlist: Boolean
    //@SerializedName("rated") val rated: Rated
) : Serializable