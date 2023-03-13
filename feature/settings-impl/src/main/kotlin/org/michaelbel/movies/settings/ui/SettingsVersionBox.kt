package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.VersionPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SettingsLanguageBox(
    modifier: Modifier = Modifier,
    appVersionData: AppVersionData
) {
    ConstraintLayout(
        modifier = modifier
            .testTag("ConstraintLayout")
    ) {
        val (icon, version, code) = createRefs()
        createHorizontalChain(icon, version, code, chainStyle = ChainStyle.Packed)

        Icon(
            imageVector = MoviesIcons.MovieFilter,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(icon) {
                    width = Dimension.value(24.dp)
                    height = Dimension.value(24.dp)
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(version.start)
                    bottom.linkTo(parent.bottom)
                },
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(R.string.settings_app_version_name, appVersionData.version),
            modifier = Modifier
                .constrainAs(version) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(icon.end)
                    top.linkTo(icon.top)
                    end.linkTo(code.start)
                    bottom.linkTo(icon.bottom)
                }
                .padding(start = 4.dp)
                .testTag("TitleText"),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = stringResource(R.string.settings_app_version_code, appVersionData.code),
            modifier = Modifier
                .constrainAs(code) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(version.end)
                    top.linkTo(version.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(version.bottom)
                }
                .padding(start = 2.dp)
                .testTag("ValueText"),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
@DevicePreviews
private fun SettingsLanguageBoxPreview(
    @PreviewParameter(VersionPreviewParameterProvider::class) appVersionData: AppVersionData
) {
    MoviesTheme {
        SettingsLanguageBox(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.primaryContainer),
            appVersionData = appVersionData
        )
    }
}