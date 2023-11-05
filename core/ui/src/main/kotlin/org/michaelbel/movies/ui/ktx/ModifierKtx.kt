package org.michaelbel.movies.ui.ktx

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo

fun Modifier.clickableWithoutRipple(
    block: () -> Unit
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickableWithoutRipple"
        value = block
    }
) {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = { block() }
    )
}