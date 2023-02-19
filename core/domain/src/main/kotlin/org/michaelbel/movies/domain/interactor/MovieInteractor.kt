package org.michaelbel.movies.domain.interactor

import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.entities.Either
import org.michaelbel.movies.entities.MovieData

interface MovieInteractor {

    suspend fun movieList(list: String, page: Int): Pair<List<MovieData>, Int>

    suspend fun movieDetails(movieId: Int): Either<MovieDb>
}