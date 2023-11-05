package org.michaelbel.movies.interactor.usecase

import javax.inject.Inject
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.Either
import org.michaelbel.movies.network.ScreenState
import org.michaelbel.movies.network.handle
import org.michaelbel.movies.persistence.database.entity.MovieDb

class MovieDetailsCase @Inject constructor(
    private val interactor: Interactor
) {
    suspend operator fun invoke(movieId: Int): ScreenState {
        val movieDetails: Either<MovieDb> = interactor.movieDetails(movieId)
        movieDetails.handle(
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