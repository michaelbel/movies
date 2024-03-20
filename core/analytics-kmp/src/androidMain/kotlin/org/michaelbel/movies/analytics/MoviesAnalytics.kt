@file:Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "NON_ACTUAL_MEMBER_DECLARED_IN_EXPECT_NON_FINAL_CLASSIFIER_ACTUALIZATION_WARNING"
)

package org.michaelbel.movies.analytics

import android.os.Bundle
import org.michaelbel.movies.analytics.model.BaseEvent

actual interface MoviesAnalytics {

    fun trackDestination(route: String?, arguments: Bundle?)

    fun logEvent(event: BaseEvent)
}