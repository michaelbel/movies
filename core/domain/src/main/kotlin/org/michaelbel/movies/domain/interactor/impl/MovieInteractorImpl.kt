package org.michaelbel.movies.domain.interactor.impl

import javax.inject.Inject
import org.michaelbel.movies.domain.interactor.MovieInteractor
import org.michaelbel.movies.domain.repository.MovieRepository
import org.michaelbel.movies.entities.Either
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.entities.MovieDetailsData

class MovieInteractorImpl @Inject constructor(
    private val movieRepository: MovieRepository,
): MovieInteractor {

    override suspend fun movieList(list: String, page: Int): Pair<List<MovieData>, Int> {
        return movieRepository.movieList(list, page)
    }

    override suspend fun movieDetails(movieId: Long): Either<MovieDetailsData> {
        return movieRepository.movieDetails(movieId)
    }
}