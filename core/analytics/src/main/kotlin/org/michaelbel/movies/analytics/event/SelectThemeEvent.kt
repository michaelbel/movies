package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.constants.MoviesEvents
import org.michaelbel.movies.analytics.model.BaseEvent
import org.michaelbel.movies.analytics.constants.MoviesParams

class SelectThemeEvent(
    theme: String
): BaseEvent(MoviesEvents.SETTINGS_SELECT_THEME) {

    init {
        add(MoviesParams.PARAM_SELECTED_THEME, theme)
    }
}