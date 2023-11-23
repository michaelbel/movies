package org.michaelbel.movies.feed.remote

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import org.michaelbel.movies.common.exceptions.FeedEmptyException
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.ktx.isEmpty
import org.michaelbel.movies.network.ktx.isPaginationReached
import org.michaelbel.movies.network.ktx.nextPage
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.entity.MovieDb

class MoviesRemoteMediator(
    private val interactor: Interactor,
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
                    interactor.page(movieList) ?: return reachedResult
                }
            }

            val moviesResult: Result<MovieResponse> = interactor.moviesResult(
                movieList = movieList,
                page = loadKey ?: 1
            )

            if (moviesResult.isEmpty) {
                throw FeedEmptyException
            }

            if (loadType == LoadType.REFRESH) {
                interactor.run {
                    removePagingKey(movieList)
                    removeAllMovies(movieList)
                }
            }
            interactor.run {
                insertPagingKey(movieList, moviesResult.nextPage)
                insertAllMovies(movieList, moviesResult.results)
            }

            MediatorResult.Success(endOfPaginationReached = moviesResult.isPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}