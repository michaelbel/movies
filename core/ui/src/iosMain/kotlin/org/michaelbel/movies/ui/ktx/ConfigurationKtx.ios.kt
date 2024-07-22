package org.michaelbel.movies.ui.ktx

import androidx.compose.runtime.Composable
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation

actual val isPortrait: Boolean
    get() = UIDevice.currentDevice.orientation in listOf(UIDeviceOrientation.UIDeviceOrientationPortrait, UIDeviceOrientation.UIDeviceOrientationPortraitUpsideDown)

actual fun statusBarStyle(detectDarkMode: Boolean): Any {
    return Any()
}

@Composable
actual fun navigationBarStyle(detectDarkMode: Boolean): Any {
    return Any()
}