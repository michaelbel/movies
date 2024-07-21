@file:Suppress("unused", "FunctionName")

package org.michaelbel.movies

import androidx.compose.ui.window.ComposeUIViewController
import org.michaelbel.movies.IosMainContent
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        IosMainContent()
    }
}