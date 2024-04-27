@file:OptIn(ExperimentalPagingApi::class)

package org.michaelbel.movies.interactor.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.network.ktx.isEmpty
import org.michaelbel.movies.network.ktx.isPaginationReached
import org.michaelbel.movies.network.ktx.nextPage
import org.michaelbel.movies.persistence.database.MoviesDatabase
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.PagingKeyRepository

class FeedMoviesRemoteMediator(
    private val localeInteractor: LocaleInteractor,
    private val pagingKeyRepository: PagingKeyRepository,
    private val movieRepository: MovieRepository,
    private val moviesDatabase: MoviesDatabase,
    private val pagingKey: PagingKey
): RemoteMediator<Int, MoviePojo>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MoviePojo>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> pagingKeyRepository.prevPage(pagingKey)
                LoadType.APPEND -> pagingKeyRepository.page(pagingKey)
            } ?: return MediatorResult.Success(endOfPaginationReached = true)

            val moviesResult = movieRepository.moviesResult(pagingKey, localeInteractor.language, loadKey)

            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pagingKeyRepository.removePagingKey(pagingKey)
                    movieRepository.removeMovies(pagingKey)
                }

                if (moviesResult.isEmpty) {
                    throw PageEmptyException
                }

                pagingKeyRepository.insertPagingKey(pagingKey, moviesResult.nextPage, moviesResult.totalPages)
                movieRepository.insertMovies(pagingKey, moviesResult.page, moviesResult.results)
            }

            MediatorResult.Success(endOfPaginationReached = moviesResult.isPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}