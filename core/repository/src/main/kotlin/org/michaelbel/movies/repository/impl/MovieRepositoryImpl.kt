package org.michaelbel.movies.repository.impl

import androidx.paging.PagingSource
import javax.inject.Inject
import javax.inject.Singleton
import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.network.Either
import org.michaelbel.movies.network.isTmdbApiKeyEmpty
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.network.response
import org.michaelbel.movies.network.service.movie.MovieService
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.dao.ktx.isEmpty
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.ktx.checkApiKeyNotNullException
import org.michaelbel.movies.repository.ktx.mapToMovieDb

@Singleton
internal class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao,
    private val localeController: LocaleController
): MovieRepository {

    override fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb> {
        return movieDao.pagingSource(pagingKey)
    }

    override suspend fun moviesResult(movieList: String, page: Int): Result<MovieResponse> {
        if (isTmdbApiKeyEmpty && movieDao.isEmpty(MovieDb.MOVIES_LOCAL_LIST)) {
            checkApiKeyNotNullException()
        }

        return movieService.movies(
            list = movieList,
            language = localeController.language,
            page = page
        )
    }

    override suspend fun movieDetails(movieId: Int): Either<MovieDb> {
        return response {
            val movieDb: MovieDb? = movieDao.movieById(movieId)

            if (movieDb != null) {
                movieDb
            } else {
                val movie: Movie = movieService.movie(
                    id = movieId,
                    language = localeController.language
                )
                movie.mapToMovieDb
            }
        }
    }

    override suspend fun removeAllMovies(pagingKey: String) {
        movieDao.removeAllMovies(pagingKey)
    }

    override suspend fun insertAllMovies(pagingKey: String, movies: List<MovieResponse>) {
        val maxPosition: Int = movieDao.maxPosition(pagingKey) ?: 0
        val moviesDb: List<MovieDb> = movies.mapIndexed { index, movieResponse ->
            movieResponse.mapToMovieDb(
                movieList = pagingKey,
                position = maxPosition.plus(index).plus(1)
            )
        }
        movieDao.insertAllMovies(moviesDb)
    }
}