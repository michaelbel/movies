package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.domain.usecase.TraktSaveSearchHistoryMovieUseCase.Params
import org.michaelbel.movies.persistence.database.TraktMoviePersistence
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo.Companion.MOVIES_TRAKT_SEARCH_HISTORY
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo.Companion.TRAKT_SEARCH_PREFIX
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import kotlin.time.Clock

class TraktSaveSearchHistoryMovieUseCase(
    private val traktMoviePersistence: TraktMoviePersistence,
    dispatchers: SharedDispatchers
): UseCase<Params, Unit>(dispatchers.io) {

    override suspend fun execute(params: Params) {
        val movie = traktMoviePersistence.movieById(TRAKT_SEARCH_PREFIX + params.searchQuery, params.traktId) ?: return
        traktMoviePersistence.upsert(
            movie.copy(
                traktList = MOVIES_TRAKT_SEARCH_HISTORY,
                dateAdded = Clock.System.now().toEpochMilliseconds()
            )
        )
    }

    data class Params(
        val searchQuery: String,
        val traktId: MovieId
    )
}
