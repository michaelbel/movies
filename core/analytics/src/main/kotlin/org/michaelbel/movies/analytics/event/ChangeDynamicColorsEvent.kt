package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.model.MoviesEvents
import org.michaelbel.movies.analytics.model.BaseEvent
import org.michaelbel.movies.analytics.model.MoviesParams

class ChangeDynamicColorsEvent(
    enabled: Boolean
): BaseEvent(MoviesEvents.SETTINGS_CHANGE_DYNAMIC_COLORS) {

    init {
        add(MoviesParams.PARAM_DYNAMIC_COLORS_ENABLED, enabled)
    }
}