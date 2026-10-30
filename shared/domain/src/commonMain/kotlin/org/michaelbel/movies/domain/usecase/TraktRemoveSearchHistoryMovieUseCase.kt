@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.persistence.database.TraktMoviePersistence
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo.Companion.MOVIES_TRAKT_SEARCH_HISTORY
import org.michaelbel.movies.persistence.database.typealiases.MovieId

class TraktRemoveSearchHistoryMovieUseCase(
    private val traktMoviePersistence: TraktMoviePersistence,
    dispatchers: SharedDispatchers
): UseCase<MovieId, Unit>(dispatchers.io) {

    override suspend fun execute(traktId: MovieId) {
        traktMoviePersistence.removeMovie(MOVIES_TRAKT_SEARCH_HISTORY, traktId)
    }
}
