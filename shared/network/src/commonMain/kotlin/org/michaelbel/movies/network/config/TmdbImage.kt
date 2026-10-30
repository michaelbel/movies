package org.michaelbel.movies.network.config

import org.michaelbel.movies.network.model.image.BackdropSize
import org.michaelbel.movies.network.model.image.PosterSize
import org.michaelbel.movies.network.model.image.ProfileSize

/**
 * See [TMDB Image Basics](https://developer.themoviedb.org/docs/image-basics)
 *
 * See [TMDB Images Configuration](https://developer.themoviedb.org/reference/configuration-details)
 */
private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p"

val String.formatPosterImage: String
    get() = when {
        startsWith("http") -> this
        else -> "$TMDB_IMAGE_BASE_URL/${PosterSize.W780.size}/$this"
    }

val String.formatBackdropImage: String
    get() = when {
        startsWith("http") -> this
        else -> "$TMDB_IMAGE_BASE_URL/${BackdropSize.W1280.size}/$this"
    }

val String.formatMovieColumnPosterImage: String
    get() = when {
        startsWith("http") -> this
        else -> "$TMDB_IMAGE_BASE_URL/${PosterSize.W500.size}/$this"
    }

val String.formatMovieRowBackdropImage: String
    get() = when {
        startsWith("http") -> this
        else -> "$TMDB_IMAGE_BASE_URL/${BackdropSize.W780.size}/$this"
    }

val String.formatProfileImage: String
    get() = when {
        startsWith("http") -> this
        else -> "$TMDB_IMAGE_BASE_URL/${ProfileSize.W185.size}/$this"
    }

val String.isNotOriginal: Boolean
    get() = !contains("original".toRegex())

fun String.formatImage(size: String): String {
    return when {
        startsWith("http") -> this
        else -> "$TMDB_IMAGE_BASE_URL/$size/$this"
    }
}
