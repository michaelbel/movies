package org.michaelbel.movies.hms.review

import android.app.Activity
import javax.inject.Inject
import org.michaelbel.movies.platform.main.review.ReviewService

internal class ReviewServiceImpl @Inject constructor(): ReviewService {

    override fun requestReview(activity: Activity) {}
}