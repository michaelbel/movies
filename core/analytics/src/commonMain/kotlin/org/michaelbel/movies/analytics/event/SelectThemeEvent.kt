package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.constants.MoviesEvents
import org.michaelbel.movies.analytics.constants.MoviesParams
import org.michaelbel.movies.analytics.model.BaseEvent

class SelectThemeEvent(
    theme: String
): BaseEvent(MoviesEvents.SETTINGS_SELECT_THEME) {

    init {
        add(MoviesParams.PARAM_SELECTED_THEME, theme)
    }
}