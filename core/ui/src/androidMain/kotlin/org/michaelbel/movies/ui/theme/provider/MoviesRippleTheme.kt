/*@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.ui.theme.provider

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RippleConfiguration
import androidx.compose.ui.graphics.Color

fun moviesRippleConfiguration(
    color: Color
): RippleConfiguration {
    return RippleConfiguration(
        color = color,
        rippleAlpha = RippleAlpha(
            pressedAlpha = 0.2F,
            focusedAlpha = 0.4F,
            draggedAlpha = 0.4F,
            hoveredAlpha = 0.4F
        ),
    )
}*/