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
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.MovieInteractor
import org.michaelbel.movies.interactor.ktx.nameOrLocalList
import org.michaelbel.movies.interactor.remote.FeedMoviesRemoteMediator
import org.michaelbel.movies.interactor.remote.SearchMoviesRemoteMediator
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.MoviesDatabase
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.persistence.database.typealiases.Query
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.PagingKeyRepository
import org.michaelbel.movies.repository.SearchRepository

internal class MovieInteractorImpl(
    private val dispatchers: MoviesDispatchers,
    private val localeInteractor: LocaleInteractor,
    private val searchRepository: SearchRepository,
    private val movieRepository: MovieRepository,
    private val pagingKeyRepository: PagingKeyRepository,
    private val moviesDatabase: MoviesDatabase
): MovieInteractor {

    override fun moviesPagingData(
        movieList: MovieList
    ): Flow<PagingData<MoviePojo>> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieResponse.DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = FeedMoviesRemoteMediator(
                localeInteractor = localeInteractor,
                movieRepository = movieRepository,
                pagingKeyRepository = pagingKeyRepository,
                moviesDatabase = moviesDatabase,
                pagingKey = MovieList.name(movieList)
            ),
            pagingSourceFactory = { movieRepository.moviesPagingSource(movieList.nameOrLocalList) }
        ).flow
    }

    override fun moviesPagingData(
        searchQuery: Query
    ): Flow<PagingData<MoviePojo>> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieResponse.DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = SearchMoviesRemoteMediator(
                localeInteractor = localeInteractor,
                pagingKeyRepository = pagingKeyRepository,
                searchRepository = searchRepository,
                movieRepository = movieRepository,
                moviesDatabase = moviesDatabase,
                query = searchQuery
            ),
            pagingSourceFactory = { movieRepository.moviesPagingSource(searchQuery) }
        ).flow
    }

    override fun moviesPagingSource(
        pagingKey: PagingKey
    ): PagingSource<Int, MoviePojo> {
        return movieRepository.moviesPagingSource(pagingKey)
    }

    override fun moviesFlow(
        pagingKey: PagingKey,
        limit: Limit
    ): Flow<List<MoviePojo>> {
        return movieRepository.moviesFlow(pagingKey, limit)
    }

    override suspend fun moviesWidget(): List<MovieDbMini> {
        return withContext(dispatchers.io) { movieRepository.moviesWidget(localeInteractor.language) }
    }

    override suspend fun movie(
        pagingKey: PagingKey,
        movieId: MovieId
    ): MoviePojo {
        return withContext(dispatchers.io) { movieRepository.movie(pagingKey, movieId) }
    }

    override suspend fun movieDetails(
        pagingKey: PagingKey,
        movieId: MovieId
    ): MoviePojo {
        return withContext(dispatchers.io) { movieRepository.movieDetails(pagingKey, localeInteractor.language, movieId) }
    }

    override suspend fun removeMovies(
        pagingKey: PagingKey
    ) {
        return withContext(dispatchers.io) { movieRepository.removeMovies(pagingKey) }
    }

    override suspend fun removeMovie(
        pagingKey: PagingKey,
        movieId: MovieId
    ) {
        return withContext(dispatchers.io) { movieRepository.removeMovie(pagingKey, movieId) }
    }

    override suspend fun insertMovie(
        pagingKey: PagingKey,
        movie: MoviePojo
    ) {
        return withContext(dispatchers.io) { movieRepository.insertMovie(pagingKey, movie) }
    }

    override suspend fun updateMovieColors(
        movieId: MovieId,
        containerColor: Int,
        onContainerColor: Int
    ) {
        return withContext(dispatchers.io) {
            movieRepository.updateMovieColors(movieId, containerColor, onContainerColor)
        }
    }
}