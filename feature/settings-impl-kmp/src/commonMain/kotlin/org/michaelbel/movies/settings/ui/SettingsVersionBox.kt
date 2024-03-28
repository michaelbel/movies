@file:OptIn(ExperimentalResourceApi::class)

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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SettingsVersionBox(
    appVersionData: AppVersionData,
    versionName: String,
    versionCode: Long,
    isDebug: Boolean,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (icon, version, code, flavor, debug) = createRefs()
        createHorizontalChain(icon, version, code, flavor, debug, chainStyle = ChainStyle.Packed)

        Icon(
            imageVector = MoviesIcons.MovieFilter,
            contentDescription = MoviesContentDescriptionCommon.None,
            modifier = Modifier.constrainAs(icon) {
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
            text = stringResource(MoviesStrings.settings_app_version_name, versionName),
            modifier = Modifier
                .constrainAs(version) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(icon.end)
                    top.linkTo(icon.top)
                    end.linkTo(code.start)
                    bottom.linkTo(icon.bottom)
                }
                .padding(start = 4.dp),
            style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        Text(
            text = stringResource(MoviesStrings.settings_app_version_code, versionCode),
            modifier = Modifier
                .constrainAs(code) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(version.end)
                    top.linkTo(icon.top)
                    end.linkTo(flavor.start)
                    bottom.linkTo(icon.bottom)
                }
                .padding(start = 2.dp),
            style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.primary)
        )

        Text(
            text = appVersionData.flavor,
            modifier = Modifier
                .constrainAs(flavor) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(code.end)
                    top.linkTo(icon.top)
                    end.linkTo(if (isDebug) debug.start else parent.end)
                    bottom.linkTo(icon.bottom)
                }
                .padding(start = 2.dp),
            style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        if (isDebug) {
            Text(
                text = stringResource(MoviesStrings.settings_app_debug),
                modifier = Modifier
                    .constrainAs(debug) {
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                        start.linkTo(flavor.end)
                        top.linkTo(icon.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(icon.bottom)
                    }
                    .padding(start = 2.dp),
                style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }
}

@Composable
/*@DevicePreviews*/
private fun SettingsVersionBoxPreview(
    /*@PreviewParameter(VersionPreviewParameterProvider::class)*/ appVersionData: AppVersionData
) {
    MoviesTheme {
        SettingsVersionBox(
            appVersionData = appVersionData,
            versionName = "1.0.0",
            versionCode = 1,
            isDebug = true,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
/*@Preview*/
private fun SettingsVersionBoxAmoledPreview(
    /*@PreviewParameter(VersionPreviewParameterProvider::class)*/ appVersionData: AppVersionData
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SettingsVersionBox(
            appVersionData = appVersionData,
            versionName = "1.0.0",
            versionCode = 1,
            isDebug = true,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}