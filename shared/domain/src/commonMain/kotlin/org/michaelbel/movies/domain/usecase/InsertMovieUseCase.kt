package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.persistence.database.MoviePersistence
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import kotlin.time.Clock
import org.michaelbel.movies.domain.usecase.InsertMovieUseCase.Params

class InsertMovieUseCase(
    private val moviePersistence: MoviePersistence,
    dispatchers: SharedDispatchers
): UseCase<Params, Unit>(dispatchers.io) {

    override suspend fun execute(params: Params) {
        val maxPosition = moviePersistence.maxPosition(params.pagingKey)
        moviePersistence.upsert(
            params.movie.copy(
                movieList = params.pagingKey,
                dateAdded = Clock.System.now().toEpochMilliseconds(),
                position = maxPosition.plus(1)
            )
        )
    }

    data class Params(
        val pagingKey: PagingKey,
        val movie: MoviePojo
    )
}
