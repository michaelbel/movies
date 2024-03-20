@file:OptIn(ExperimentalPagingApi::class)
@file:Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE"
)

package org.michaelbel.movies.interactor.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import org.michaelbel.movies.persistence.database.entity.MovieDb

expect class FeedMoviesRemoteMediator: RemoteMediator<Int, MovieDb>