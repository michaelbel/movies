package org.michaelbel.movies.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.persistence.database.TraktMoviePersistence
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.moviePojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.domain.usecase.TraktMovieFlowUseCase.Params

class TraktMovieFlowUseCase(
    private val traktMoviePersistence: TraktMoviePersistence,
    dispatchers: SharedDispatchers
): FlowUseCase<Params, MoviePojo?>(dispatchers.io) {

    override fun execute(params: Params): Flow<MoviePojo?> {
        return traktMoviePersistence.movieFlow(params.traktList, params.traktId).map { traktMovieDb -> traktMovieDb?.moviePojo }
    }

    data class Params(
        val traktList: PagingKey,
        val traktId: MovieId
    )
}
