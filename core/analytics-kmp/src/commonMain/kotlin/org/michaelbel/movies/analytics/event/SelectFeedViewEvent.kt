package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.constants.MoviesEvents
import org.michaelbel.movies.analytics.constants.MoviesParams
import org.michaelbel.movies.analytics.model.BaseEvent

class SelectFeedViewEvent(
    feedView: String
): BaseEvent(MoviesEvents.SETTINGS_FEED_VIEW) {

    init {
        add(MoviesParams.PARAM_SELECTED_FEED_VIEW, feedView)
    }
}