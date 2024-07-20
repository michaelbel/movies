@file:Suppress("unused", "FunctionName")

package org.michaelbel.movies.main

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        MainContent()
    }
}