package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountStates(
    @SerialName("id") val id: Int,
    @SerialName("favorite") val favorite: Boolean,
    @SerialName("watchlist") val watchlist: Boolean
    //@SerialName("rated") val rated: Rated
)