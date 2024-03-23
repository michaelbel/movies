@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.settings.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.compose.iconbutton.BackIcon
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SettingsToolbar(
    modifier: Modifier = Modifier,
    topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    onNavigationIconClick: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Text(
                text = "Settings",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        modifier = modifier.testTag("TopAppBar"),
        navigationIcon = {
            BackIcon(
                onClick = onNavigationIconClick
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
        ),
        scrollBehavior = topAppBarScrollBehavior
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