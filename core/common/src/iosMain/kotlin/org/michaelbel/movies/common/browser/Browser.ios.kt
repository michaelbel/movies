package org.michaelbel.movies.common.browser

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

fun openUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url)
    if (nsUrl != null) {
        UIApplication.sharedApplication().openURL(nsUrl)
    }
}