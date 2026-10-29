@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.event.SelectMovieListEvent
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.persistence.datastore.TmdbPreferences

class SelectMovieListUseCase(
    private val preferences: TmdbPreferences,
    private val analytics: MoviesAnalytics,
    dispatchers: SharedDispatchers
): UseCase<MovieList, Unit>(dispatchers.io) {

    override suspend fun execute(movieList: MovieList) {
        preferences.setValue(TmdbPreferences.PreferenceKey.PreferenceMovieListKey, movieList.toString())
        analytics.logEvent(SelectMovieListEvent(movieList.toString()))
    }
}
