package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.BooleanPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SettingsDynamicColorsBox(
    modifier: Modifier = Modifier,
    isDynamicColorsEnabled: Boolean
) {
    ConstraintLayout(
        modifier = modifier
            .testTag("ConstraintLayout")
    ) {
        val (title, value) = createRefs()

        Text(
            text = stringResource(R.string.settings_dynamic_colors),
            modifier = Modifier
                .constrainAs(title) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("Text"),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        Switch(
            checked = isDynamicColorsEnabled,
            onCheckedChange = null,
            modifier = Modifier
                .constrainAs(value) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("Switch")
        )
    }
}

@Composable
@DevicePreviews
private fun SettingsDynamicColorsBoxPreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) isEnabled: Boolean
) {
    MoviesTheme {
        SettingsDynamicColorsBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            isDynamicColorsEnabled = isEnabled
        )
    }
}