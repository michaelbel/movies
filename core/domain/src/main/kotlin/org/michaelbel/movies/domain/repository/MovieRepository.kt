package org.michaelbel.movies.domain.repository

import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.entities.Either
import org.michaelbel.movies.entities.MovieData

interface MovieRepository {

    suspend fun movieList(list: String, page: Int): Pair<List<MovieData>, Int>

    suspend fun movieDetails(movieId: Int): Either<MovieDb>
}