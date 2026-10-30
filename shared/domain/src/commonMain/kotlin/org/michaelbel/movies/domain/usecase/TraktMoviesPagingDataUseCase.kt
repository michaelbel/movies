@file:OptIn(ExperimentalPagingApi::class)
@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package org.michaelbel.movies.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.common.list.TraktList
import org.michaelbel.movies.domain.usecase.remote.TraktMoviesRemoteMediator
import org.michaelbel.movies.network.config.TRAKT_PAGE_SIZE
import org.michaelbel.movies.persistence.database.TraktDatabase
import org.michaelbel.movies.persistence.database.TraktMoviePersistence
import org.michaelbel.movies.persistence.database.TraktPagingKeyPersistence
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.moviePojo

class TraktMoviesPagingDataUseCase(
    private val traktMoviePersistence: TraktMoviePersistence,
    private val traktPagingKeyPersistence: TraktPagingKeyPersistence,
    private val traktPagingKeyPageUseCase: TraktPagingKeyPageUseCase,
    private val pagingKeyPrevPageUseCase: PagingKeyPrevPageUseCase,
    private val traktMoviesResultUseCase: TraktMoviesResultUseCase,
    private val traktDatabase: TraktDatabase,
    dispatchers: SharedDispatchers
): FlowUseCase<TraktList, PagingData<MoviePojo>>(dispatchers.io) {

    override fun execute(traktList: TraktList): Flow<PagingData<MoviePojo>> {
        val pagingKey = TraktList.name(traktList)
        return Pager(
            config = PagingConfig(
                pageSize = TRAKT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = TraktMoviesRemoteMediator(
                traktPagingKeyPageUseCase = traktPagingKeyPageUseCase,
                pagingKeyPrevPageUseCase = pagingKeyPrevPageUseCase,
                traktMoviesResultUseCase = traktMoviesResultUseCase,
                traktMoviePersistence = traktMoviePersistence,
                traktPagingKeyPersistence = traktPagingKeyPersistence,
                traktDatabase = traktDatabase,
                pagingKey = pagingKey
            ),
            pagingSourceFactory = { traktMoviePersistence.pagingSource(pagingKey) }
        ).flow.map { pagingData -> pagingData.map { traktMovieDb -> traktMovieDb.moviePojo } }
    }
}
