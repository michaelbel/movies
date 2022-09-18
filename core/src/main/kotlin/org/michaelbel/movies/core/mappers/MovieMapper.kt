package org.michaelbel.movies.core.mappers

import java.util.Locale
import javax.inject.Inject
import org.michaelbel.movies.core.entities.MovieData
import org.michaelbel.movies.core.entities.MovieDetailsData
import org.michaelbel.movies.core.model.Movie
import org.michaelbel.movies.core.model.MovieResponse

class MovieMapper @Inject constructor() {

    private val feedFileSize: String by lazy {
        String.format(Locale.ENGLISH, TMDB_IMAGE_FILE_SIZE_W500, TMDB_IMAGE_FILE_SIZE_W500)
    }

    fun mapToMovieDataList(response: List<MovieResponse>): List<MovieData> {
        return response.map { movieResponse: MovieResponse -> movieResponse.toMovieData() }
    }

    fun mapToMovieDetailsData(response: Movie): MovieDetailsData {
        return response.toMovieDetailsData()
    }

    private fun formatImageUrl(path: String, size: String): String {
        return if (path.isNotEmpty()) {
            val imageSize: String = size.ifEmpty { TMDB_IMAGE_FILE_SIZE_ORIGINAL }
            String.format(Locale.ENGLISH, TMDB_IMAGE_BASE_URL, imageSize, path)
        } else {
            IMAGE_EMPTY_URL
        }
    }

    private fun MovieResponse.toMovieData(): MovieData {
        return MovieData(
            id = id,
            overview = overview.orEmpty(),
            posterPath = posterPath,
            backdropPath = formatImageUrl(backdropPath.orEmpty(), feedFileSize),
            releaseDate = releaseDate,
            title = title,
            voteAverage = voteAverage,
            genreIds = genreIds
        )
    }

    private fun Movie.toMovieDetailsData(): MovieDetailsData {
        return MovieDetailsData(
            id = id,
            overview = overview.orEmpty(),
            posterPath = posterPath.orEmpty(),
            backdropPath = formatImageUrl(backdropPath.orEmpty(), feedFileSize),
            releaseDate = releaseDate.orEmpty(),
            title = title.orEmpty(),
            voteAverage = voteAverage
        )
    }

    private companion object {
        private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/%s/%s"
        private const val TMDB_IMAGE_FILE_SIZE_ORIGINAL = "original"
        private const val TMDB_IMAGE_FILE_SIZE_W500 = "w500"
        private const val IMAGE_EMPTY_URL = "https://null"
    }
}