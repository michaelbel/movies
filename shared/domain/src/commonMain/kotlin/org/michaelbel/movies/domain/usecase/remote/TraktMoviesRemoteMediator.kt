@file:OptIn(ExperimentalPagingApi::class)

package org.michaelbel.movies.domain.usecase.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.domain.usecase.PagingKeyPrevPageUseCase
import org.michaelbel.movies.domain.usecase.TraktMoviesResultUseCase
import org.michaelbel.movies.domain.usecase.TraktPagingKeyPageUseCase
import org.michaelbel.movies.persistence.database.TraktDatabase
import org.michaelbel.movies.persistence.database.TraktMoviePersistence
import org.michaelbel.movies.persistence.database.TraktPagingKeyPersistence
import org.michaelbel.movies.persistence.database.entity.TraktMovieDb
import org.michaelbel.movies.persistence.database.entity.TraktPagingKeyDb
import org.michaelbel.movies.persistence.database.ktx.traktMovieDb
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class TraktMoviesRemoteMediator(
    private val traktPagingKeyPageUseCase: TraktPagingKeyPageUseCase,
    private val pagingKeyPrevPageUseCase: PagingKeyPrevPageUseCase,
    private val traktMoviesResultUseCase: TraktMoviesResultUseCase,
    private val traktMoviePersistence: TraktMoviePersistence,
    private val traktPagingKeyPersistence: TraktPagingKeyPersistence,
    private val traktDatabase: TraktDatabase,
    private val pagingKey: PagingKey
): RemoteMediator<Int, TraktMovieDb>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TraktMovieDb>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> pagingKeyPrevPageUseCase(pagingKey).getOrThrow()
                LoadType.APPEND -> traktPagingKeyPageUseCase(pagingKey).getOrThrow()
            } ?: return MediatorResult.Success(endOfPaginationReached = true)

            val params = TraktMoviesResultUseCase.Params(pagingKey, loadKey)
            val moviesResult = traktMoviesResultUseCase(params).getOrThrow()

            traktDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    traktPagingKeyPersistence.removePagingKey(pagingKey)
                    traktMoviePersistence.removeMovies(pagingKey)
                }

                if (moviesResult.results.isEmpty()) {
                    throw PageEmptyException()
                }

                traktPagingKeyPersistence.upsertPagingKey(
                    TraktPagingKeyDb(
                        pagingKey = pagingKey,
                        page = moviesResult.page + 1,
                        totalPages = moviesResult.totalPages
                    )
                )

                val maxPosition = traktMoviePersistence.maxPosition(pagingKey)
                val moviesDb = moviesResult.results.mapIndexed { index, movieResponse ->
                    movieResponse.traktMovieDb(
                        traktList = pagingKey,
                        page = moviesResult.page,
                        position = if (maxPosition == 0) index else maxPosition.plus(index).plus(1)
                    )
                }
                traktMoviePersistence.upsert(moviesDb)
            }

            MediatorResult.Success(endOfPaginationReached = moviesResult.page >= moviesResult.totalPages)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
