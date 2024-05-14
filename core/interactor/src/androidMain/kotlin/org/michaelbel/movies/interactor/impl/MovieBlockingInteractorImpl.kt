@file:OptIn(ExperimentalPagingApi::class)

package org.michaelbel.movies.interactor.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.MovieBlockingInteractor
import org.michaelbel.movies.interactor.ktx.nameOrLocalList
import org.michaelbel.movies.interactor.remote.FeedMoviesRemoteMediator
import org.michaelbel.movies.interactor.remote.SearchMoviesRemoteMediator
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.MoviesDatabase
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.Query
import org.michaelbel.movies.repository.MovieBlockingRepository
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.PagingKeyRepository
import org.michaelbel.movies.repository.SearchRepository

internal class MovieBlockingInteractorImpl(
    private val localeInteractor: LocaleInteractor,
    private val searchRepository: SearchRepository,
    private val movieRepository: MovieRepository,
    private val movieBlockingRepository: MovieBlockingRepository,
    private val pagingKeyRepository: PagingKeyRepository,
    private val moviesDatabase: MoviesDatabase
): MovieBlockingInteractor {

    override fun moviesPagingData(
        movieList: MovieList
    ): Flow<PagingData<MoviePojo>> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieResponse.DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = FeedMoviesRemoteMediator(
                localeInteractor = localeInteractor,
                movieRepository = movieRepository,
                pagingKeyRepository = pagingKeyRepository,
                moviesDatabase = moviesDatabase,
                pagingKey = MovieList.name(movieList)
            ),
            pagingSourceFactory = { movieBlockingRepository.moviesPagingSource(movieList.nameOrLocalList) }
        ).flow
    }

    override fun moviesPagingData(
        searchQuery: Query
    ): Flow<PagingData<MoviePojo>> {
        return Pager(
            config = PagingConfig(
                pageSize = MovieResponse.DEFAULT_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = SearchMoviesRemoteMediator(
                localeInteractor = localeInteractor,
                pagingKeyRepository = pagingKeyRepository,
                searchRepository = searchRepository,
                movieRepository = movieRepository,
                moviesDatabase = moviesDatabase,
                query = searchQuery
            ),
            pagingSourceFactory = { movieBlockingRepository.moviesPagingSource(searchQuery) }
        ).flow
    }
}