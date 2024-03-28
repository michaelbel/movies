package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.config.TMDB_MOVIE_URL
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import java.util.Locale

val MoviePojo.isNotEmpty: Boolean
    get() = this != MoviePojo.Empty

val MoviePojo.url: String
    get() = String.format(Locale.US, TMDB_MOVIE_URL, movieId)

val MoviePojo?.orEmpty: MoviePojo
    get() = this ?: MoviePojo.Empty