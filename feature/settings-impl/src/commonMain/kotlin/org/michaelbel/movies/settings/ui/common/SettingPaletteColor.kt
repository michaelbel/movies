package org.michaelbel.movies.settings.ui.common

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.color.TonalPalettes
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.theme.a1
import org.michaelbel.movies.ui.theme.a2
import org.michaelbel.movies.ui.theme.a3

@Composable
internal fun RowScope.SettingPaletteColor(
    tonalPalettes: TonalPalettes,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerSize by animateDpAsState(
        targetValue = if (isSelected) 28.dp else 0.dp,
        label = "containerSize"
    )
    val iconSize by animateDpAsState(
        targetValue = if (isSelected) 16.dp else 0.dp,
        label = "iconSize"
    )

    Box(
        modifier = modifier
            .padding(4.dp)
            .sizeIn(maxHeight = 80.dp, maxWidth = 80.dp, minHeight = 64.dp, minWidth = 64.dp)
            .weight(1F, false)
            .aspectRatio(1F)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(onClick = onClick)
    ) {
        val color1 = 80.a1(tonalPalettes)
        val color2 = 90.a2(tonalPalettes)
        val color3 = 60.a3(tonalPalettes)

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .drawBehind { drawCircle(color1) }
                    .align(Alignment.Center)
            ) {
                Box(
                    modifier = Modifier
                        .background(color2)
                        .align(Alignment.BottomStart)
                        .size(24.dp)
                )

                Box(
                    modifier = Modifier
                        .background(color3)
                        .align(Alignment.BottomEnd)
                        .size(24.dp)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .size(containerSize)
                ) {
                    Icon(
                        imageVector = MoviesIcons.Check,
                        contentDescription = MoviesContentDescriptionCommon.None,
                        modifier = Modifier
                            .size(iconSize)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}