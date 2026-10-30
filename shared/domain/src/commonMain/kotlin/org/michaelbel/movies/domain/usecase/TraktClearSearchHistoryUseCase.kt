package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.persistence.database.TraktMoviePersistence
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo.Companion.MOVIES_TRAKT_SEARCH_HISTORY

class TraktClearSearchHistoryUseCase(
    private val traktMoviePersistence: TraktMoviePersistence,
    dispatchers: SharedDispatchers
): UseCase<Unit, Unit>(dispatchers.io) {

    override suspend fun execute(params: Unit) {
        traktMoviePersistence.removeMovies(MOVIES_TRAKT_SEARCH_HISTORY)
    }
}
