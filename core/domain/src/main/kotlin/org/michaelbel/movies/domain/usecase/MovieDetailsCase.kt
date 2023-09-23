package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.entities.handle
import org.michaelbel.movies.entities.lce.ScreenState
import org.michaelbel.movies.interactor.Interactor
import javax.inject.Inject

class MovieDetailsCase @Inject constructor(
    private val interactor: Interactor
) {
    suspend operator fun invoke(movieId: Int): ScreenState {
        interactor.movieDetails(movieId).handle(
            success = { movieDetailsData ->
                return ScreenState.Content(movieDetailsData)
            },
            failure = { throwable ->
                return ScreenState.Failure(throwable)
            }
        )
        return ScreenState.Loading
    }
}