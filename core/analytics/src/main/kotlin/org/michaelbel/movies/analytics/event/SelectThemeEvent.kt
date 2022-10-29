package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.model.MoviesEvents
import org.michaelbel.movies.analytics.model.BaseEvent
import org.michaelbel.movies.analytics.model.MoviesParams

class SelectThemeEvent(
    theme: String
): BaseEvent(MoviesEvents.SETTINGS_SELECT_THEME) {

    init {
        add(MoviesParams.PARAM_SELECTED_THEME, theme)
    }
}