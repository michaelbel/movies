package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.settings.ktx.themeText
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.ThemePreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SettingsThemeBox(
    modifier: Modifier = Modifier,
    themes: List<AppTheme>,
    currentTheme: AppTheme,
    onThemeSelect: (AppTheme) -> Unit
) {
    var themeDialog: Boolean by remember { mutableStateOf(false) }

    if (themeDialog) {
        SettingThemeDialog(
            themes = themes,
            currentTheme = currentTheme,
            onThemeSelect = onThemeSelect,
            onDismissRequest = {
                themeDialog = false
            }
        )
    }

    ConstraintLayout(
        modifier = modifier
            .clickable {
                themeDialog = true
            }
            .testTag("ConstraintLayout")
    ) {
        val (title, value) = createRefs()

        Text(
            text = stringResource(R.string.settings_theme),
            modifier = Modifier
                .constrainAs(title) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("TitleText"),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        Text(
            text = currentTheme.themeText,
            modifier = Modifier
                .constrainAs(value) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("ValueText"),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
@DevicePreviews
private fun SettingsThemeBoxPreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) theme: AppTheme
) {
    MoviesTheme {
        SettingsThemeBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            themes = listOf(AppTheme.FollowSystem, AppTheme.NightNo, AppTheme.NightYes),
            currentTheme = theme,
            onThemeSelect = {}
        )
    }
}