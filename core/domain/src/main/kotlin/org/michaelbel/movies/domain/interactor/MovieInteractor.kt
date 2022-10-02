package org.michaelbel.movies.domain.interactor

import org.michaelbel.movies.entities.Either
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.entities.MovieDetailsData

interface MovieInteractor {

    suspend fun movieList(list: String, page: Int): Pair<List<MovieData>, Int>

    suspend fun movieDetails(movieId: Long): Either<MovieDetailsData>
}