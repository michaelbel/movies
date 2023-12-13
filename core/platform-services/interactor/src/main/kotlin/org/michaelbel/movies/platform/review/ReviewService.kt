package org.michaelbel.movies.platform.review

import android.app.Activity

interface ReviewService {

    fun requestReview(activity: Activity)
}