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
import org.michaelbel.movies.domain.usecase.remote.TraktSearchMoviesRemoteMediator
import org.michaelbel.movies.network.config.TRAKT_PAGE_SIZE
import org.michaelbel.movies.persistence.database.TraktDatabase
import org.michaelbel.movies.persistence.database.TraktMoviePersistence
import org.michaelbel.movies.persistence.database.TraktPagingKeyPersistence
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo.Companion.TRAKT_SEARCH_PREFIX
import org.michaelbel.movies.persistence.database.ktx.moviePojo

class TraktSearchMoviesPagingDataUseCase(
    private val traktMoviePersistence: TraktMoviePersistence,
    private val traktPagingKeyPersistence: TraktPagingKeyPersistence,
    private val traktPagingKeyPageUseCase: TraktPagingKeyPageUseCase,
    private val pagingKeyPrevPageUseCase: PagingKeyPrevPageUseCase,
    private val traktSearchMoviesResultUseCase: TraktSearchMoviesResultUseCase,
    private val traktDatabase: TraktDatabase,
    dispatchers: SharedDispatchers
): FlowUseCase<String, PagingData<MoviePojo>>(dispatchers.io) {

    override fun execute(query: String): Flow<PagingData<MoviePojo>> {
        return Pager(
            config = PagingConfig(
                pageSize = TRAKT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = TraktSearchMoviesRemoteMediator(
                traktPagingKeyPageUseCase = traktPagingKeyPageUseCase,
                pagingKeyPrevPageUseCase = pagingKeyPrevPageUseCase,
                traktSearchMoviesResultUseCase = traktSearchMoviesResultUseCase,
                traktMoviePersistence = traktMoviePersistence,
                traktPagingKeyPersistence = traktPagingKeyPersistence,
                traktDatabase = traktDatabase,
                query = query
            ),
            pagingSourceFactory = { traktMoviePersistence.pagingSource(TRAKT_SEARCH_PREFIX + query) }
        ).flow.map { pagingData -> pagingData.map { traktMovieDb -> traktMovieDb.moviePojo } }
    }
}
