package org.michaelbel.movies.common.review

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory

@Composable
fun rememberReviewManager(): ReviewManager {
    val context: Context = LocalContext.current
    val reviewManager: ReviewManager = ReviewManagerFactory.create(context)
    return remember { reviewManager }
}