package org.michaelbel.moviemade.data.model

import com.google.gson.annotations.SerializedName

data class AccountStates(
    @SerializedName("id") val id: Int,
    @SerializedName("favorite") val favorite: Boolean,
    @SerializedName("watchlist") val watchlist: Boolean
    //@SerializedName("rated") val rated: Rated
)