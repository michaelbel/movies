@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.common.browser

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult

expect fun openUrl(
    resultContract: ManagedActivityResultLauncher<Intent, ActivityResult>,
    toolbarColor: Int,
    url: String
)