package org.michaelbel.movies.widget.entity

import kotlinx.serialization.Serializable
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

@Serializable
internal data class MovieData(
    val movieList: PagingKey,
    val movieId: MovieId,
    val movieTitle: String
)