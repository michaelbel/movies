@file:OptIn(ExperimentalPagingApi::class)
@file:Suppress(
    "EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE",
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)

package org.michaelbel.movies.interactor.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import org.michaelbel.movies.persistence.database.entity.MovieDb

expect class SearchMoviesRemoteMediator: RemoteMediator<Int, MovieDb>