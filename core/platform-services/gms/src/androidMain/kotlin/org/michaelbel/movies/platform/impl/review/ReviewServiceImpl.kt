package org.michaelbel.movies.platform.impl.review

import android.app.Activity
import com.google.android.play.core.review.ReviewManager
import org.michaelbel.movies.platform.review.ReviewService

class ReviewServiceImpl(
    private val reviewManager: ReviewManager
): ReviewService {

    override fun requestReview(activity: Any) {
        reviewManager.requestReviewFlow().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                reviewManager.launchReviewFlow(activity as Activity, task.result)
            }
        }
    }
}