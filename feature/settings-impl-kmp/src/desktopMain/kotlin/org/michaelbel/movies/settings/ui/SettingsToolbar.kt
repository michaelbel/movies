@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)

package org.michaelbel.movies.settings.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import movies.feature.settings_impl_kmp.generated.resources.Res
import movies.feature.settings_impl_kmp.generated.resources.settings_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.compose.iconbutton.BackIcon
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SettingsToolbar(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.settings_title),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        modifier = modifier,
        navigationIcon = {
            BackIcon(
                onClick = onNavigationIconClick
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
        )
    )
}

@Composable
@Preview
private fun SettingsToolbarPreview() {
    MoviesTheme {
        SettingsToolbar(
            modifier = Modifier.statusBarsPadding(),
            onNavigationIconClick = {}
        )
    }
}

@Composable
@Preview
private fun SettingsToolbarAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SettingsToolbar(
            modifier = Modifier.statusBarsPadding(),
            onNavigationIconClick = {}
        )
    }
}