package org.michaelbel.movies.common.browser

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.core.net.toUri

@Composable
actual fun navigateToUrl(url: String): () -> Unit {
    val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    val toolbarColor = MaterialTheme.colorScheme.primary.toArgb()
    val colorSchemeParams = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(toolbarColor)
        .build()
    val customTabsIntentBuilder = CustomTabsIntent.Builder().apply {
        setDefaultColorSchemeParams(colorSchemeParams)
    }
    val customTabsIntent = customTabsIntentBuilder.build().apply {
        intent.data = url.toUri()
    }
    return { resultContract.launch(customTabsIntent.intent) }
}