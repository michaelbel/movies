package org.michaelbel.movies.interactor.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.MovieInteractor
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.repository.MovieRepository

class MovieInteractorImpl(
    private val dispatchers: SharedDispatchers,
    private val localeInteractor: LocaleInteractor,
    private val movieRepository: MovieRepository
): MovieInteractor {

    override fun movieFlow(pagingKey: PagingKey, movieId: MovieId): Flow<MoviePojo?> {
        return movieRepository.movieFlow(pagingKey, movieId)
    }

    override suspend fun moviesWidget(): List<MovieDbMini> {
        return withContext(dispatchers.io) { movieRepository.moviesWidget(localeInteractor.language) }
    }

    override suspend fun movie(pagingKey: PagingKey, movieId: MovieId): MoviePojo {
        return withContext(dispatchers.io) { movieRepository.movie(pagingKey, movieId) }
    }

    override suspend fun movieDetails(pagingKey: PagingKey, movieId: MovieId): MoviePojo {
        return withContext(dispatchers.io) { movieRepository.movieDetails(pagingKey, localeInteractor.language, movieId) }
    }

    override suspend fun removeMovies(pagingKey: PagingKey) {
        return withContext(dispatchers.io) { movieRepository.removeMovies(pagingKey) }
    }

    override suspend fun removeMovie(pagingKey: PagingKey, movieId: MovieId) {
        return withContext(dispatchers.io) { movieRepository.removeMovie(pagingKey, movieId) }
    }

    override suspend fun insertMovie(pagingKey: PagingKey, movie: MoviePojo) {
        return withContext(dispatchers.io) { movieRepository.insertMovie(pagingKey, movie) }
    }

    override suspend fun updateFavorite(movieId: MovieId, favorite: Boolean) {
        return withContext(dispatchers.io) { movieRepository.updateFavorite(movieId, favorite) }
    }
}
