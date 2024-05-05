@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.platform.impl.review

import android.app.Activity
import org.michaelbel.movies.platform.review.ReviewService

actual class ReviewServiceImpl: ReviewService {

    override fun requestReview(activity: Activity) {}
}