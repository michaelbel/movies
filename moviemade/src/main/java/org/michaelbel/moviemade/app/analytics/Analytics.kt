package org.michaelbel.moviemade.app.analytics

import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class Analytics @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) {

    fun trackScreen(screenName: String?) {
        val bundle = bundleOf(FirebaseAnalytics.Event.SELECT_ITEM to screenName)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    fun logEvent(event: String) {
        firebaseAnalytics.logEvent(event, null)
    }
}