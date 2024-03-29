@file:OptIn(ExperimentalPagingApi::class)

package org.michaelbel.movies.interactor.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.interactor.MovieInteractor
import org.michaelbel.movies.interactor.ktx.nameOrLocalList
import org.michaelbel.movies.interactor.remote.FeedMoviesRemoteMediator
import org.michaelbel.movies.interactor.remote.SearchMoviesRemoteMediator
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.MoviesDatabase
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.PagingKeyRepository
import org.michaelbel.movies.repository.SearchRepository

internal class MovieInteractorImpl(
    private val dispatchers: MoviesDispatchers,
    private val searchRepository: SearchRepository,
    private val movieRepository: MovieRepository,
    private val pagingKeyRepository: PagingKeyRepository,
    private val moviesDatabase: MoviesDatabase
): MovieInteractor {

    override fun moviesPagingData(movieList: MovieList): Flow<PagingData<MoviePojo>> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieResponse.DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = FeedMoviesRemoteMediator(
                movieRepository = movieRepository,
                pagingKeyRepository = pagingKeyRepository,
                moviesDatabase = moviesDatabase,
                movieList = MovieList.name(movieList)
            ),
            pagingSourceFactory = { movieRepository.moviesPagingSource(movieList.nameOrLocalList) }
        ).flow
    }

    override fun moviesPagingData(searchQuery: String): Flow<PagingData<MoviePojo>> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieResponse.DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = SearchMoviesRemoteMediator(
                pagingKeyRepository = pagingKeyRepository,
                searchRepository = searchRepository,
                movieRepository = movieRepository,
                moviesDatabase = moviesDatabase,
                query = searchQuery
            ),
            pagingSourceFactory = { movieRepository.moviesPagingSource(searchQuery) }
        ).flow
    }

    override fun moviesPagingSource(pagingKey: String): PagingSource<Int, MoviePojo> {
        return movieRepository.moviesPagingSource(pagingKey)
    }

    override fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MoviePojo>> {
        return movieRepository.moviesFlow(pagingKey, limit)
    }

    override suspend fun moviesWidget(): List<MovieDbMini> {
        return withContext(dispatchers.io) { movieRepository.moviesWidget() }
    }

    override suspend fun movie(pagingKey: String, movieId: Int): MoviePojo {
        return withContext(dispatchers.io) { movieRepository.movie(pagingKey, movieId) }
    }

    override suspend fun movieDetails(pagingKey: String, movieId: Int): MoviePojo {
        return withContext(dispatchers.io) { movieRepository.movieDetails(pagingKey, movieId) }
    }

    override suspend fun removeMovies(pagingKey: String) {
        return withContext(dispatchers.io) { movieRepository.removeMovies(pagingKey) }
    }

    override suspend fun removeMovie(pagingKey: String, movieId: Int) {
        return withContext(dispatchers.io) { movieRepository.removeMovie(pagingKey, movieId) }
    }

    override suspend fun insertMovie(pagingKey: String, movie: MoviePojo) {
        return withContext(dispatchers.io) { movieRepository.insertMovie(pagingKey, movie) }
    }

    override suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int) {
        return withContext(dispatchers.io) {
            movieRepository.updateMovieColors(movieId, containerColor, onContainerColor)
        }
    }
}