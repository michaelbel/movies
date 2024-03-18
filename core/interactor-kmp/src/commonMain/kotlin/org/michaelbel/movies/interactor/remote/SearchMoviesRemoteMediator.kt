@file:OptIn(ExperimentalPagingApi::class)
@file:Suppress(
    "EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE",
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)

package org.michaelbel.movies.interactor.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.network.ktx.isEmpty
import org.michaelbel.movies.network.ktx.isPaginationReached
import org.michaelbel.movies.network.ktx.nextPage
import org.michaelbel.movies.persistence.database.AppDatabase
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.PagingKeyRepository
import org.michaelbel.movies.repository.SearchRepository

expect class SearchMoviesRemoteMediator: RemoteMediator<Int, MovieDb>