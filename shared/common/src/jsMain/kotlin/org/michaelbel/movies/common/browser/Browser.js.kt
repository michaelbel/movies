package org.michaelbel.movies.common.browser

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler

actual fun tmdbAuthRedirectUrl(): String = defaultTmdbAuthRedirectUrl

@Composable
actual fun navigateToUrl(url: String): () -> Unit {
    val uriHandler = LocalUriHandler.current
    return {
        uriHandler.openUri(url)
    }
}
