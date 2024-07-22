package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.settings.model.SettingsData
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SettingsVersionBox(
    aboutData: SettingsData.AboutData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = MoviesIcons.MovieFilter,
            contentDescription = MoviesContentDescriptionCommon.None,
            modifier = Modifier.padding(vertical = 2.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(MoviesStrings.settings_app_version_name, aboutData.versionName),
            modifier = Modifier.padding(start = 4.dp),
            style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        Text(
            text = stringResource(MoviesStrings.settings_app_version_code, aboutData.versionCode),
            modifier = Modifier.padding(start = 2.dp),
            style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.primary)
        )

        Text(
            text = aboutData.flavor,
            modifier = Modifier.padding(start = 2.dp),
            style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        if (aboutData.isDebug) {
            Text(
                text = stringResource(MoviesStrings.settings_app_debug),
                modifier = Modifier.padding(start = 2.dp),
                style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }
}

@Preview
@Composable
private fun SettingsVersionBoxPreview(
    /*@PreviewParameter(VersionPreviewParameterProvider::class)*/ appVersionData: AppVersionData
) {
    MoviesTheme {
        SettingsVersionBox(
            aboutData = SettingsData.AboutData(
                isFeatureEnabled = true,
                versionName = "1.0.0",
                versionCode = 1,
                flavor = "GMS",
                isDebug = true,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}