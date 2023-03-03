package org.michaelbel.movies.domain.data.ktx

import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.entities.TMDB_MOVIE_URL
import java.util.Locale

val MovieDb.isNotEmpty: Boolean
    get() = this != MovieDb.Empty

val MovieDb.url: String
    get() = String.format(Locale.US, TMDB_MOVIE_URL, movieId)