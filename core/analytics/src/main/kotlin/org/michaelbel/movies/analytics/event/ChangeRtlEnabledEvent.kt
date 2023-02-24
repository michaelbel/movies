package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.model.BaseEvent
import org.michaelbel.movies.analytics.constants.MoviesEvents
import org.michaelbel.movies.analytics.constants.MoviesParams

class ChangeRtlEnabledEvent(
    enabled: Boolean
): BaseEvent(MoviesEvents.SETTINGS_CHANGE_RTL_ENABLED) {

    init {
        add(MoviesParams.PARAM_RTL_ENABLED, enabled)
    }
}