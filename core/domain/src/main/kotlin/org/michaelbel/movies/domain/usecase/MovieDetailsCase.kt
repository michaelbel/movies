package org.michaelbel.movies.domain.usecase

import javax.inject.Inject
import org.michaelbel.movies.domain.interactor.MovieInteractor
import org.michaelbel.movies.entities.handle
import org.michaelbel.movies.entities.lce.ScreenState

class MovieDetailsCase @Inject constructor(
    private val movieInteractor: MovieInteractor
) {
    suspend operator fun invoke(movieId: Int): ScreenState {
        movieInteractor.movieDetails(movieId).handle(
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