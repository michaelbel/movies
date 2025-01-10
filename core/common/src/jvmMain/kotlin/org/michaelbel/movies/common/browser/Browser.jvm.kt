package org.michaelbel.movies.common.browser

import androidx.compose.runtime.Composable
import java.awt.Desktop
import java.net.URI

@Composable
actual fun navigateToUrl(url: String): () -> Unit {
    val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
    return {
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            desktop.browse(URI.create(url))
        }
    }
}