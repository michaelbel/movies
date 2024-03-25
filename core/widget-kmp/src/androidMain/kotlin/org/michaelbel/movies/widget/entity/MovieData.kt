package org.michaelbel.movies.widget.entity

import kotlinx.serialization.Serializable

@Serializable
internal data class MovieData(
    val movieList: String,
    val movieId: Int,
    val movieTitle: String
)