package org.michaelbel.movies.domain.mediator

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.domain.interactor.movie.MovieInteractor
import org.michaelbel.movies.network.ktx.isPaginationReached
import org.michaelbel.movies.network.ktx.nextPage
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result

class MoviesRemoteMediator(
    private val movieInteractor: MovieInteractor,
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
                    movieInteractor.page(movieList) ?: return reachedResult
                }
            }

            val moviesResult: Result<MovieResponse> = movieInteractor.moviesResult(
                movieList = movieList,
                page = loadKey ?: 1
            )

            if (loadType == LoadType.REFRESH) {
                movieInteractor.run {
                    removePagingKey(movieList)
                    removeAllMovies(movieList)
                }
            }
            movieInteractor.run {
                insertPagingKey(movieList, moviesResult.nextPage)
                insertAllMovies(movieList, moviesResult.results)
            }

            MediatorResult.Success(endOfPaginationReached = moviesResult.isPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}