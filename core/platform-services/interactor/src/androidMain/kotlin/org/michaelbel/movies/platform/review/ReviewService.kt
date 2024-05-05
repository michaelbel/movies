@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.platform.review

import android.app.Activity

actual interface ReviewService {

    fun requestReview(activity: Activity)
}