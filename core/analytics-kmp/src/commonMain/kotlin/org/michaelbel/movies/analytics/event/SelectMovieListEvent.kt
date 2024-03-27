package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.constants.MoviesEvents
import org.michaelbel.movies.analytics.constants.MoviesParams
import org.michaelbel.movies.analytics.model.BaseEvent

class SelectMovieListEvent(
    movieList: String
): BaseEvent(MoviesEvents.SETTINGS_MOVIE_LIST) {

    init {
        add(MoviesParams.PARAM_SELECTED_MOVIE_LIST, movieList)
    }
}