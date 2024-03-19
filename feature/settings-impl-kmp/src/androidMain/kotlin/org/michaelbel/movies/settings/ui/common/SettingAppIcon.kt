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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.appicon.isEnabled
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.IconAliasPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
actual fun RowScope.SettingAppIcon(
    iconAlias: IconAlias,
    onClick: (IconAlias) -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current

    val containerSize by animateDpAsState(
        targetValue = if (context.isEnabled(iconAlias)) 28.dp else 0.dp,
        label = "containerSize"
    )
    val iconSize by animateDpAsState(
        targetValue = if (context.isEnabled(iconAlias)) 16.dp else 0.dp,
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
            contentDescription = stringResource(MoviesContentDescription.AppIcon),
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
                contentDescription = MoviesContentDescription.None,
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.Center),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
@DevicePreviews
private fun SettingAppIconPreview(
    @PreviewParameter(IconAliasPreviewParameterProvider::class) iconAlias: IconAlias
) {
    MoviesTheme {
        Row {
            SettingAppIcon(
                iconAlias = iconAlias,
                onClick = {}
            )
        }
    }
}

@Composable
@Preview
private fun SettingAppIconAmoledPreview(
    @PreviewParameter(IconAliasPreviewParameterProvider::class) iconAlias: IconAlias
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        Row {
            SettingAppIcon(
                iconAlias = iconAlias,
                onClick = {}
            )
        }
    }
}