@file:OptIn(ExperimentalPagingApi::class)

package org.michaelbel.movies.interactor.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.network.ktx.isEmpty
import org.michaelbel.movies.network.ktx.isPaginationReached
import org.michaelbel.movies.network.ktx.nextPage
import org.michaelbel.movies.persistence.database.MoviesDatabase
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.PagingKeyRepository

class FeedMoviesRemoteMediator(
    private val pagingKeyRepository: PagingKeyRepository,
    private val movieRepository: MovieRepository,
    private val moviesDatabase: MoviesDatabase,
    private val movieList: String
): RemoteMediator<Int, MoviePojo>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MoviePojo>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> pagingKeyRepository.prevPage(movieList)
                LoadType.APPEND -> pagingKeyRepository.page(movieList)
            } ?: return MediatorResult.Success(endOfPaginationReached = true)

            val moviesResult = movieRepository.moviesResult(movieList, loadKey)

            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pagingKeyRepository.removePagingKey(movieList)
                    movieRepository.removeMovies(movieList)
                }

                if (moviesResult.isEmpty) {
                    throw PageEmptyException
                }

                pagingKeyRepository.insertPagingKey(movieList, moviesResult.nextPage, moviesResult.totalPages)
                movieRepository.insertMovies(movieList, moviesResult.page, moviesResult.results)
            }

            MediatorResult.Success(endOfPaginationReached = moviesResult.isPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}