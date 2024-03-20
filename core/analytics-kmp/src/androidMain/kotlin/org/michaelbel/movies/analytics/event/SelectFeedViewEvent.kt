@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.constants.MoviesEvents
import org.michaelbel.movies.analytics.constants.MoviesParams
import org.michaelbel.movies.analytics.model.BaseEvent

actual class SelectFeedViewEvent actual constructor(
    feedView: String
): BaseEvent(MoviesEvents.SETTINGS_FEED_VIEW) {

    init {
        add(MoviesParams.PARAM_SELECTED_FEED_VIEW, feedView)
    }
}