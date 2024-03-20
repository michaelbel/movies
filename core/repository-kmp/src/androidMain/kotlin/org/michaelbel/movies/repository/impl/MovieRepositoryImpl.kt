package org.michaelbel.movies.repository.impl

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.exceptions.MovieDetailsException
import org.michaelbel.movies.common.exceptions.MoviesUpcomingException
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.network.isTmdbApiKeyEmpty
import org.michaelbel.movies.network.ktor.KtorMovieService
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.network.retrofit.RetrofitMovieService
import org.michaelbel.movies.persistence.database.MoviePersistence
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.ktx.movieDb
import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.ktx.checkApiKeyNotNullException
import org.michaelbel.movies.repository.ktx.mapToMovieDb
import javax.inject.Inject
import javax.inject.Singleton

/**
 * You can replace [ktorMovieService] with [retrofitMovieService] to use it.
 */
@Singleton
internal class MovieRepositoryImpl @Inject constructor(
    private val retrofitMovieService: RetrofitMovieService,
    private val ktorMovieService: KtorMovieService,
    private val moviePersistence: MoviePersistence,
    private val localeController: LocaleController
): MovieRepository {

    override fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb> {
        return moviePersistence.pagingSource(pagingKey)
    }

    override fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MovieDb>> {
        return moviePersistence.moviesFlow(pagingKey, limit)
    }

    override suspend fun moviesResult(movieList: String, page: Int): Result<MovieResponse> {
        if (isTmdbApiKeyEmpty && moviePersistence.isEmpty(MovieDb.MOVIES_LOCAL_LIST)) {
            checkApiKeyNotNullException()
        }

        return ktorMovieService.movies(
            list = movieList,
            language = localeController.language,
            page = page
        )
    }

    override suspend fun movie(pagingKey: String, movieId: Int): MovieDb {
        return moviePersistence.movieById(pagingKey, movieId).orEmpty
    }

    override suspend fun movieDetails(pagingKey: String, movieId: Int): MovieDb {
        return try {
            moviePersistence.movieById(pagingKey, movieId) ?: ktorMovieService.movie(movieId, localeController.language).mapToMovieDb
        } catch (ignored: Exception) {
            throw MovieDetailsException
        }
    }

    override suspend fun moviesWidget(): List<MovieDbMini> {
        return try {
            val movieResult = ktorMovieService.movies(
                list = MovieList.Upcoming.name,
                language = localeController.language,
                page = 1
            )
            val moviesDb = movieResult.results.mapIndexed { index, movieResponse ->
                movieResponse.movieDb(
                    movieList = MovieDb.MOVIES_WIDGET,
                    position = index.plus(1)
                )
            }
            moviePersistence.removeMovies(MovieDb.MOVIES_WIDGET)
            moviePersistence.insertMovies(moviesDb)
            moviePersistence.moviesMini(MovieDb.MOVIES_WIDGET, MovieResponse.DEFAULT_PAGE_SIZE)
        } catch (ignored: Exception) {
            moviePersistence.moviesMini(MovieDb.MOVIES_WIDGET, MovieResponse.DEFAULT_PAGE_SIZE).ifEmpty {
                throw MoviesUpcomingException
            }
        }
    }

    override suspend fun removeMovies(pagingKey: String) {
        moviePersistence.removeMovies(pagingKey)
    }

    override suspend fun removeMovie(pagingKey: String, movieId: Int) {
        moviePersistence.removeMovie(pagingKey, movieId)
    }

    override suspend fun insertMovies(pagingKey: String, page: Int, movies: List<MovieResponse>) {
        val maxPosition = moviePersistence.maxPosition(pagingKey) ?: 0
        val moviesDb = movies.mapIndexed { index, movieResponse ->
            movieResponse.mapToMovieDb(
                movieList = pagingKey,
                page = page,
                position = if (maxPosition == 0) index else maxPosition.plus(index).plus(1)
            )
        }
        moviePersistence.insertMovies(moviesDb)
    }

    override suspend fun insertMovie(pagingKey: String, movie: MovieDb) {
        val maxPosition = moviePersistence.maxPosition(pagingKey) ?: 0
        moviePersistence.insertMovie(
            movie.copy(
                movieList = pagingKey,
                dateAdded = System.currentTimeMillis(),
                position = maxPosition.plus(1)
            )
        )
    }

    override suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int) {
        moviePersistence.updateMovieColors(movieId, containerColor, onContainerColor)
    }
}