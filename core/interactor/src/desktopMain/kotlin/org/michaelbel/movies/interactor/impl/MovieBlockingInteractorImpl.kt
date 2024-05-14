@file:OptIn(ExperimentalPagingApi::class)

package org.michaelbel.movies.interactor.impl

import androidx.paging.ExperimentalPagingApi
import org.michaelbel.movies.interactor.MovieBlockingInteractor
import org.michaelbel.movies.repository.MovieBlockingRepository

internal class MovieBlockingInteractorImpl(
    private val movieBlockingRepository: MovieBlockingRepository
): MovieBlockingInteractor