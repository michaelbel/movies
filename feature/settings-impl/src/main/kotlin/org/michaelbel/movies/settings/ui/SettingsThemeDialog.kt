package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.settings.ktx.themeTextRes
import org.michaelbel.movies.ui.icon.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme
import org.michaelbel.movies.ui.theme.model.AppTheme
import org.michaelbel.movies.ui.theme.preview.ThemesPreviewParameterProvider

@Composable
internal fun SettingThemeDialog(
    themes: List<AppTheme>,
    currentTheme: AppTheme,
    onThemeSelect: (AppTheme) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onDismissRequest,
            ) {
                Text(
                    text = stringResource(R.string.settings_action_cancel),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        icon = {
            Icon(
                painter = painterResource(MoviesIcons.ThemeLightDark),
                contentDescription = null
            )
        },
        title = {
            Text(
                text = stringResource(R.string.settings_theme),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            SettingThemeDialogContent(
                themes = themes,
                currentTheme = currentTheme,
                onThemeSelect = { theme ->
                    onThemeSelect(theme)
                    onDismissRequest()
                }
            )
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    )
}

@Composable
private fun SettingThemeDialogContent(
    themes: List<AppTheme>,
    currentTheme: AppTheme,
    onThemeSelect: (AppTheme) -> Unit
) {
    Column {
        themes.forEach { theme: AppTheme ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        onThemeSelect(theme)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentTheme == theme,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)
                    ),
                    modifier = Modifier
                        .padding(
                            start = 16.dp
                        )
                )

                Text(
                    text = stringResource(theme.themeTextRes),
                    modifier = Modifier
                        .padding(
                            start = 8.dp
                        ),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
@DevicePreviews
private fun SettingThemeDialogPreview(
    @PreviewParameter(ThemesPreviewParameterProvider::class) theme: AppTheme
) {
    MoviesTheme {
        SettingThemeDialog(
            themes = listOf(
                AppTheme.NightNo,
                AppTheme.NightYes,
                AppTheme.FollowSystem
            ),
            currentTheme = theme,
            onThemeSelect = {},
            onDismissRequest = {}
        )
    }
}