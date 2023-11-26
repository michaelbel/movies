package org.michaelbel.movies.search.remote

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.ktx.isEmpty
import org.michaelbel.movies.network.ktx.isPaginationReached
import org.michaelbel.movies.network.ktx.nextPage
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.entity.MovieDb

class SearchMoviesRemoteMediator(
    private val interactor: Interactor,
    private val query: String
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
                    interactor.page(query) ?: return reachedResult
                }
            }

            if (query.isEmpty()) {
                throw PageEmptyException
            }

            val moviesResult: Result<MovieResponse> = interactor.searchMoviesResult(
                query = query,
                page = loadKey ?: 1
            )

            if (moviesResult.isEmpty) {
                throw PageEmptyException
            }

            if (loadType == LoadType.REFRESH) {
                interactor.run {
                    removePagingKey(query)
                    removeAllMovies(query)
                }
            }
            interactor.run {
                insertPagingKey(query, moviesResult.nextPage)
                insertAllMovies(query, moviesResult.results)
            }

            MediatorResult.Success(endOfPaginationReached = moviesResult.isPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}