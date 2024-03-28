package org.michaelbel.movies.ui.ktx

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.dp

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

internal val PageContentColumnModifier: Modifier
    @Composable get() = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .clip(MaterialTheme.shapes.small)
        .background(MaterialTheme.colorScheme.inversePrimary)

internal val PageContentGridModifier: Modifier
    @Composable get() = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)
        .clip(MaterialTheme.shapes.small)
        .background(MaterialTheme.colorScheme.inversePrimary)

internal val PageContentStaggeredGridModifier: Modifier
    @Composable get() = Modifier
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.small)
        .background(MaterialTheme.colorScheme.inversePrimary)