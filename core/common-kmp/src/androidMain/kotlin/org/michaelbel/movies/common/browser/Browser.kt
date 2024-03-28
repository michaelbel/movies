package org.michaelbel.movies.common.browser

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

fun openUrl(
    resultContract: ManagedActivityResultLauncher<Intent, ActivityResult>,
    toolbarColor: Int,
    url: String
) {
    val colorSchemeParams = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(toolbarColor)
        .build()
    val customTabsIntentBuilder = CustomTabsIntent.Builder().apply {
        setDefaultColorSchemeParams(colorSchemeParams)
    }
    val customTabsIntent = customTabsIntentBuilder.build().apply {
        intent.data = url.toUri()
    }
    resultContract.launch(customTabsIntent.intent)
}