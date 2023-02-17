package org.michaelbel.movies.domain.repository.impl

import javax.inject.Inject
import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.domain.data.dao.MovieDao
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.domain.datasource.MovieNetwork
import org.michaelbel.movies.domain.exceptions.ktx.checkApiKeyNotNullException
import org.michaelbel.movies.domain.ktx.mapToMovieData
import org.michaelbel.movies.domain.ktx.mapToMovieDb
import org.michaelbel.movies.domain.repository.MovieRepository
import org.michaelbel.movies.entities.Either
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.entities.response
import org.michaelbel.movies.entities.tmdbApiKey
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result

internal class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieNetwork,
    private val movieDao: MovieDao,
    private val localeController: LocaleController
): MovieRepository {

    override suspend fun movieList(list: String, page: Int): Pair<List<MovieData>, Int> {
        checkApiKeyNotNullException()

        val result: Result<MovieResponse> = movieApi.movies(
            list = list,
            apiKey = tmdbApiKey,
            language = localeController.language,
            page = page
        )
        movieDao.insertAll(result.results.map(MovieResponse::mapToMovieDb))
        return result.results.map(MovieResponse::mapToMovieData) to result.totalPages
    }

    override suspend fun movieDetails(movieId: Int): Either<MovieDb> {
        return response {
            val movieDb: MovieDb? = movieDao.movieById(movieId)

            if (movieDb != null) {
                movieDb
            } else {
                val movie: Movie = movieApi.movie(
                    id = movieId,
                    apiKey = tmdbApiKey,
                    language = localeController.language
                )
                movie.mapToMovieDb
            }
        }
    }
}