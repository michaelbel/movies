package org.michaelbel.movies.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.persistence.datastore.TmdbPreferences

class CurrentMovieListFlowUseCase(
    private val preferences: TmdbPreferences,
    dispatchers: SharedDispatchers
): FlowUseCase<Unit, MovieList>(dispatchers.io) {

    override fun execute(params: Unit): Flow<MovieList> {
        return preferences.getValueFlow(TmdbPreferences.PreferenceKey.PreferenceMovieListKey)
            .map { className -> MovieList.transform(className ?: MovieList.NowPlaying().toString()) }
    }
}
