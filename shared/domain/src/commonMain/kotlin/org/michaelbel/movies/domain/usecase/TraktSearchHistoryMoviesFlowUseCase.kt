package org.michaelbel.movies.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.persistence.database.TraktMoviePersistence
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo.Companion.MOVIES_TRAKT_SEARCH_HISTORY
import org.michaelbel.movies.persistence.database.ktx.moviePojo

class TraktSearchHistoryMoviesFlowUseCase(
    private val traktMoviePersistence: TraktMoviePersistence,
    dispatchers: SharedDispatchers
): FlowUseCase<Unit, List<MoviePojo>>(dispatchers.io) {

    override fun execute(params: Unit): Flow<List<MoviePojo>> {
        return traktMoviePersistence.moviesFlow(MOVIES_TRAKT_SEARCH_HISTORY).map { movies ->
            movies.map { traktMovieDb -> traktMovieDb.moviePojo }
        }
    }
}
