package org.michaelbel.movies.domain.ktx

import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.network.model.MovieResponse
import java.util.Locale

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/%s/%s"
private const val TMDB_IMAGE_FILE_SIZE_ORIGINAL = "original"
private const val TMDB_IMAGE_FILE_SIZE_W500 = "w500"
private const val IMAGE_EMPTY_URL = "https://null"

private val feedFileSize: String by lazy {
    String.format(Locale.ENGLISH, TMDB_IMAGE_FILE_SIZE_W500, TMDB_IMAGE_FILE_SIZE_W500)
}

fun formatImageUrl(path: String, size: String = feedFileSize): String {
    return if (path.isNotEmpty()) {
        val imageSize: String = size.ifEmpty { TMDB_IMAGE_FILE_SIZE_ORIGINAL }
        String.format(Locale.ENGLISH, TMDB_IMAGE_BASE_URL, imageSize, path)
    } else {
        IMAGE_EMPTY_URL
    }
}

internal fun MovieResponse.mapToMovieDb(movieList: String, position: Int): MovieDb {
    return MovieDb(
        movieList = movieList,
        dateAdded = System.currentTimeMillis(),
        position = position,
        movieId = id,
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        backdropPath = formatImageUrl(backdropPath.orEmpty()),
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage
    )
}