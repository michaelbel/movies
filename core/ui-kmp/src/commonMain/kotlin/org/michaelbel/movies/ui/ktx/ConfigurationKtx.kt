@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.ktx

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.unit.Dp

expect val screenWidth: Dp

expect val screenHeight: Dp

expect val isPortrait: Boolean

expect val displayCutoutWindowInsets: WindowInsets

expect val gridColumnsCount: Int