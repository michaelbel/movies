package org.michaelbel.movies.core.review

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager

@Composable
fun rememberReviewTask(
    reviewManager: ReviewManager
): ReviewInfo? {
    var reviewInfo: ReviewInfo? by remember { mutableStateOf(null) }
    reviewManager.requestReviewFlow().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            reviewInfo = task.result
        }
    }
    return reviewInfo
}