package org.michaelbel.movies.domain.repository.impl

import javax.inject.Inject
import org.michaelbel.movies.domain.datasource.MovieNetwork
import org.michaelbel.movies.domain.exceptions.checkApiKeyNotNullException
import org.michaelbel.movies.domain.repository.MovieRepository
import org.michaelbel.movies.entities.Either
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.entities.MovieDetailsData
import org.michaelbel.movies.entities.language
import org.michaelbel.movies.entities.mapper.MovieMapper
import org.michaelbel.movies.entities.response
import org.michaelbel.movies.entities.tmdbApiKey
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieNetwork,
    private val movieMapper: MovieMapper
): MovieRepository {

    override suspend fun movieList(list: String, page: Int): Pair<List<MovieData>, Int> {
        checkApiKeyNotNullException()

        val result: Result<MovieResponse> = movieApi.movies(
            list = list,
            apiKey = tmdbApiKey,
            language = language,
            page = page
        )
        return movieMapper.mapToMovieDataList(result.results) to result.totalPages
    }

    override suspend fun movieDetails(movieId: Long): Either<MovieDetailsData> {
        return response {
            val movie: Movie = movieApi.movie(
                id = movieId,
                apiKey = tmdbApiKey,
                language = language
            )
            movieMapper.mapToMovieDetailsData(movie)
        }
    }
}