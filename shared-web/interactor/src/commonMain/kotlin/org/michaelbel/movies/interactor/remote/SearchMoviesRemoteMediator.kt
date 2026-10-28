@file:OptIn(ExperimentalPagingApi::class)

package org.michaelbel.movies.interactor.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.CancellationException
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.network.ktx.isEmpty
import org.michaelbel.movies.network.ktx.isPaginationReached
import org.michaelbel.movies.network.ktx.nextPage
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.Query
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.PagingKeyRepository
import org.michaelbel.movies.repository.SearchRepository

class SearchMoviesRemoteMediator(
    private val language: String,
    private val movieRepository: MovieRepository,
    private val searchRepository: SearchRepository,
    private val pagingKeyRepository: PagingKeyRepository,
    private val query: Query
): RemoteMediator<Int, MoviePojo>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MoviePojo>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> pagingKeyRepository.prevPage(query)
                LoadType.APPEND -> pagingKeyRepository.page(query)
            } ?: return MediatorResult.Success(endOfPaginationReached = true)

            val moviesResult = searchRepository.searchMoviesResult(query, language, loadKey)

            movieRepository.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pagingKeyRepository.removePagingKey(query)
                    movieRepository.removeMovies(query)
                }

                if (moviesResult.isEmpty) {
                    throw PageEmptyException()
                }

                pagingKeyRepository.insertPagingKey(query, moviesResult.nextPage, moviesResult.totalPages)
                movieRepository.insertMovies(query, moviesResult.page, moviesResult.results)
            }

            MediatorResult.Success(endOfPaginationReached = moviesResult.isPaginationReached)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
