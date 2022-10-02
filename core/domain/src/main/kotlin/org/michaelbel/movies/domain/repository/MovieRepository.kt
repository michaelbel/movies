package org.michaelbel.movies.domain.repository

import org.michaelbel.movies.entities.Either
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.entities.MovieDetailsData

interface MovieRepository {

    suspend fun movieList(list: String, page: Int): Pair<List<MovieData>, Int>

    suspend fun movieDetails(movieId: Long): Either<MovieDetailsData>
}