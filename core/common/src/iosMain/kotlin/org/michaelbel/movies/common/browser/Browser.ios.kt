package org.michaelbel.movies.common.browser

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun navigateToUrl(url: String): () -> Unit {
    val nsUrl = NSURL.URLWithString(url)
    return {
        if (nsUrl != null) {
            UIApplication.sharedApplication().openURL(nsUrl)
        }
    }
}