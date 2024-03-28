@file:OptIn(ExperimentalResourceApi::class)

package org.michaelbel.movies.settings.ui.common

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun RowScope.SettingAppIcon(
    iconAlias: IconAlias,
    isEnabled: Boolean,
    onClick: (IconAlias) -> Unit,
    modifier: Modifier = Modifier
) {
    val containerSize by animateDpAsState(
        targetValue = if (isEnabled) 28.dp else 0.dp,
        label = "containerSize"
    )
    val iconSize by animateDpAsState(
        targetValue = if (isEnabled) 16.dp else 0.dp,
        label = "iconSize"
    )

    Box(
        modifier = modifier
            .padding(4.dp)
            .sizeIn(maxHeight = 80.dp, maxWidth = 80.dp, minHeight = 64.dp, minWidth = 64.dp)
            .weight(1F, false)
            .aspectRatio(1F)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.inversePrimary)
            .clickable { onClick(iconAlias) }
    ) {
        Icon(
            painter = painterResource(iconAlias.iconRes),
            contentDescription = stringResource(MoviesContentDescriptionCommon.AppIcon),
            modifier = Modifier
                .padding(8.dp)
                .clip(CircleShape)
                .align(Alignment.Center),
            tint = Color.Unspecified
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

@Composable
/*@DevicePreviews*/
private fun SettingAppIconPreview(
    /*@PreviewParameter(IconAliasPreviewParameterProvider::class)*/ iconAlias: IconAlias
) {
    MoviesTheme {
        Row {
            SettingAppIcon(
                iconAlias = iconAlias,
                isEnabled = true,
                onClick = {}
            )
        }
    }
}

@Composable
/*@Preview*/
private fun SettingAppIconAmoledPreview(
    /*@PreviewParameter(IconAliasPreviewParameterProvider::class)*/ iconAlias: IconAlias
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        Row {
            SettingAppIcon(
                iconAlias = iconAlias,
                isEnabled = true,
                onClick = {}
            )
        }
    }
}