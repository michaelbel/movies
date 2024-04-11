package org.michaelbel.movies.common.browser

import java.awt.Desktop
import java.net.URI

fun openUrl(url: String) {
    val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
        desktop.browse(URI.create(url))
    }
}