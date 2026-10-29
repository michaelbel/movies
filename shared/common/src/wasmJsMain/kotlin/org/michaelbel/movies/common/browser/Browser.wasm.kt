package org.michaelbel.movies.common.browser

import androidx.compose.runtime.Composable
import kotlinx.browser.window

actual fun tmdbAuthRedirectUrl(): String {
    return window.location.origin + window.location.pathname
}

@Composable
actual fun navigateToUrl(url: String): () -> Unit {
    return {
        val appUrl = tmdbAuthRedirectUrl() // links returning to the app must open in this tab to consume the redirect
        val opened = when {
            url.contains(appUrl) -> null
            else -> window.open(url, "_blank")
        }
        if (opened == null) {
            window.location.href = url
        }
    }
}
