package org.michaelbel.movies.interactor.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.interactor.MovieInteractor
import org.michaelbel.movies.interactor.ktx.nameOrLocalList
import org.michaelbel.movies.interactor.remote.MoviesRemoteMediator
import org.michaelbel.movies.network.Either
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.AppDatabase
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.PagingKeyRepository

@Singleton
internal class MovieInteractorImpl @Inject constructor(
    private val dispatchers: MoviesDispatchers,
    private val movieRepository: MovieRepository,
    private val pagingKeyRepository: PagingKeyRepository,
    private val database: AppDatabase,
): MovieInteractor {

    override fun moviesPagingData(movieList: MovieList): Flow<PagingData<MovieDb>> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieResponse.DEFAULT_PAGE_SIZE
            ),
            remoteMediator = MoviesRemoteMediator(
                movieRepository = movieRepository,
                pagingKeyRepository = pagingKeyRepository,
                database = database,
                movieList = movieList.name
            ),
            pagingSourceFactory = { movieRepository.moviesPagingSource(movieList.nameOrLocalList) }
        ).flow
    }

    override fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb> {
        return movieRepository.moviesPagingSource(pagingKey)
    }

    override fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MovieDb>> {
        return movieRepository.moviesFlow(
            pagingKey = pagingKey,
            limit = limit
        )
    }

    override suspend fun movie(pagingKey: String, movieId: Int): MovieDb {
        return withContext(dispatchers.io) {
            movieRepository.movie(pagingKey, movieId)
        }
    }

    override suspend fun movieDetails(movieId: Int): Either<MovieDb> {
        return withContext(dispatchers.io) {
            movieRepository.movieDetails(movieId)
        }
    }

    override suspend fun removeMovies(pagingKey: String) {
        return withContext(dispatchers.io) {
            movieRepository.removeMovies(pagingKey)
        }
    }

    override suspend fun removeMovie(pagingKey: String, movieId: Int) {
        return withContext(dispatchers.io) {
            movieRepository.removeMovie(pagingKey, movieId)
        }
    }

    override suspend fun insertMovies(pagingKey: String, movies: List<MovieResponse>) {
        return withContext(dispatchers.io) {
            movieRepository.insertMovies(pagingKey, movies)
        }
    }

    override suspend fun insertMovie(pagingKey: String, movie: MovieDb) {
        return withContext(dispatchers.io) {
            movieRepository.insertMovie(pagingKey, movie)
        }
    }
}