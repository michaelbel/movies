package org.michaelbel.movies.domain.ktx

import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.network.model.MovieResponse

private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/%s%s"
private const val TMDB_IMAGE_FILE_SIZE_ORIGINAL = "original"
private const val TMDB_IMAGE_FILE_SIZE_W500 = "w500"
private const val IMAGE_EMPTY_URL = "https://null"

fun formatImageUrl(path: String, size: String = TMDB_IMAGE_FILE_SIZE_W500): String {
    return if (path.isNotEmpty()) {
        val imageSize: String = size.ifEmpty { TMDB_IMAGE_FILE_SIZE_ORIGINAL }
        String.format(TMDB_IMAGE_BASE_URL, imageSize, path)
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