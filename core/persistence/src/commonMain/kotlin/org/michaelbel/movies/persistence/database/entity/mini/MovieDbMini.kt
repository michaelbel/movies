package org.michaelbel.movies.persistence.database.entity.mini

import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

data class MovieDbMini(
    val movieList: PagingKey,
    val movieId: MovieId,
    val title: String,
)