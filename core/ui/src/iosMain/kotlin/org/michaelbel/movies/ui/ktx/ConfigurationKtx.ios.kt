package org.michaelbel.movies.ui.ktx

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation

actual val isDebug: Boolean
    get() = true

actual val isPortrait: Boolean
    get() = UIDevice.currentDevice.orientation in listOf(UIDeviceOrientation.UIDeviceOrientationPortrait, UIDeviceOrientation.UIDeviceOrientationPortraitUpsideDown)

actual fun statusBarStyle(detectDarkMode: Boolean): Any {
    return Any()
}

@Composable
actual fun navigationBarStyle(detectDarkMode: Boolean): Any {
    return Any()
}

actual val displayCutoutWindowInsets: WindowInsets
    get() = WindowInsets(0, 0, 0, 0)