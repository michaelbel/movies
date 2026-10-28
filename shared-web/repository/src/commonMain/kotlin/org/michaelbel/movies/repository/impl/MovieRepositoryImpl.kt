@file:OptIn(ExperimentalTime::class)

package org.michaelbel.movies.repository.impl

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.exceptions.MovieDetailsException
import org.michaelbel.movies.common.exceptions.MoviesUpcomingException
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.network.AccountNetworkService
import org.michaelbel.movies.network.MovieNetworkService
import org.michaelbel.movies.network.model.Fave
import org.michaelbel.movies.network.model.Mark
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.MoviePersistence
import org.michaelbel.movies.persistence.database.MoviesDatabase
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.moviePojo
import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.repository.MovieRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class MovieRepositoryImpl(
    private val movieNetworkService: MovieNetworkService,
    private val accountNetworkService: AccountNetworkService,
    private val moviePersistence: MoviePersistence,
    private val moviesDatabase: MoviesDatabase,
    private val preferences: MoviesPreferences
): MovieRepository {

    override fun movieFlow(pagingKey: PagingKey, movieId: MovieId): Flow<MoviePojo?> {
        return moviesDatabase.movieDao.movieFlow(pagingKey, movieId)
    }

    override suspend fun movie(pagingKey: PagingKey, movieId: MovieId): MoviePojo {
        return moviePersistence.movieById(pagingKey, movieId).orEmpty
    }

    override suspend fun movieDetails(pagingKey: PagingKey, language: String, movieId: MovieId): MoviePojo {
        return try {
            moviePersistence.movieById(pagingKey, movieId) ?: movieNetworkService.movie(movieId, language).moviePojo
        } catch (_: Exception) {
            throw MovieDetailsException()
        }
    }

    override suspend fun moviesWidget(language: String): List<MovieDbMini> {
        return try {
            val movieResult = movieNetworkService.movies(
                list = MovieList.Upcoming().name,
                language = language,
                page = 1
            )
            val movies = movieResult.results.mapIndexed { index, movieResponse ->
                movieResponse.moviePojo(
                    movieList = MoviePojo.MOVIES_WIDGET,
                    position = index.plus(1)
                )
            }
            moviePersistence.removeMovies(MoviePojo.MOVIES_WIDGET)
            moviePersistence.upsert(movies)
            moviePersistence.moviesMini(MoviePojo.MOVIES_WIDGET, MovieResponse.DEFAULT_PAGE_SIZE)
        } catch (_: Exception) {
            moviePersistence.moviesMini(MoviePojo.MOVIES_WIDGET, MovieResponse.DEFAULT_PAGE_SIZE).ifEmpty {
                throw MoviesUpcomingException()
            }
        }
    }

    override suspend fun removeMovies(pagingKey: PagingKey) {
        moviePersistence.removeMovies(pagingKey)
    }

    override suspend fun removeMovie(pagingKey: PagingKey, movieId: MovieId) {
        moviePersistence.removeMovie(pagingKey, movieId)
    }

    override suspend fun insertMovie(pagingKey: PagingKey, movie: MoviePojo) {
        val maxPosition = moviePersistence.maxPosition(pagingKey)
        moviePersistence.upsert(
            movie.copy(
                movieList = pagingKey,
                dateAdded = Clock.System.now().toEpochMilliseconds(),
                position = maxPosition.plus(1)
            )
        )
    }

    override suspend fun updateFavorite(movieId: MovieId, favorite: Boolean) {
        val sessionId = preferences.getValue(MoviesPreferences.PreferenceKey.PreferenceSessionIdKey).orEmpty()
        val accountId = preferences.getValue(MoviesPreferences.PreferenceKey.PreferenceAccountKey).orEmpty()
        if (sessionId.isEmpty() || accountId == 0) return

        val movie = moviePersistence.movieById(movieId) ?: return
        val mark = accountNetworkService.markAsFavorite(
            accountId = accountId,
            sessionId = sessionId,
            fave = Fave(
                mediaType = Movie.MOVIE,
                mediaId = movieId.toLong(),
                favorite = favorite
            )
        )

        if (mark.statusCode in setOf(Mark.ADDED, Mark.UPDATED, Mark.DELETED)) {
            when (favorite) {
                true -> insertMovie(Movie.FAVORITE, movie)
                false -> removeMovie(Movie.FAVORITE, movieId)
            }
        }
    }
}
