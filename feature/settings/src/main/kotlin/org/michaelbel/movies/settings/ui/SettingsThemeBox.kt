package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.settings.R
import org.michaelbel.movies.settings.ktx.themeTextRes
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme
import org.michaelbel.movies.ui.theme.model.AppTheme
import org.michaelbel.movies.ui.theme.preview.ThemesPreviewParameterProvider

@Composable
fun SettingsThemeBox(
    modifier: Modifier = Modifier,
    currentTheme: AppTheme
) {
    ConstraintLayout(
        modifier = modifier
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
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = stringResource(currentTheme.themeTextRes),
            modifier = Modifier
                .constrainAs(value) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("ValueText"),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
@DevicePreviews
private fun SettingsThemeBoxPreview(
    @PreviewParameter(ThemesPreviewParameterProvider::class) theme: AppTheme
) {
    MoviesTheme {
        SettingsThemeBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            currentTheme = theme
        )
    }
}