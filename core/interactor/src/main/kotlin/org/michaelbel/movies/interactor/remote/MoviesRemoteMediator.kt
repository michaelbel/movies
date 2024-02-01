package org.michaelbel.movies.interactor.remote

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.network.ktx.isEmpty
import org.michaelbel.movies.network.ktx.isPaginationReached
import org.michaelbel.movies.network.ktx.nextPage
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.AppDatabase
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.PagingKeyRepository

class MoviesRemoteMediator(
    private val pagingKeyRepository: PagingKeyRepository,
    private val movieRepository: MovieRepository,
    private val database: AppDatabase,
    private val movieList: String
): RemoteMediator<Int, MovieDb>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieDb>
    ): MediatorResult {
        val reachedResult: MediatorResult = MediatorResult.Success(endOfPaginationReached = true)
        return try {
            val loadKey: Int? = when (loadType) {
                LoadType.REFRESH -> {
                    null
                }
                LoadType.PREPEND -> {
                    return reachedResult
                }
                LoadType.APPEND -> {
                    pagingKeyRepository.page(movieList) ?: return reachedResult
                }
            }

            val moviesResult: Result<MovieResponse> = movieRepository.moviesResult(
                movieList = movieList,
                page = loadKey ?: 1
            )

            if (moviesResult.isEmpty) {
                throw PageEmptyException
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pagingKeyRepository.removePagingKey(movieList)
                    movieRepository.removeMovies(movieList)
                }
                pagingKeyRepository.insertPagingKey(movieList, moviesResult.nextPage)
                movieRepository.insertMovies(movieList, moviesResult.results)
            }

            MediatorResult.Success(endOfPaginationReached = moviesResult.isPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}