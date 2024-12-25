@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.ui.compose.iconbutton.BackIcon
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.ktx.modifierDisplayCutoutWindowInsets
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SettingsToolbar(
    modifier: Modifier = Modifier,
    topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    isNavigationIconVisible: Boolean,
    onNavigationIconClick: () -> Unit,
    onClick: () -> Unit,
) {
    LargeTopAppBar(
        title = {
            Text(
                text = stringResource(MoviesStrings.settings_title),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        modifier = modifier.clickableWithoutRipple(onClick),
        navigationIcon = if (isNavigationIconVisible) {
            {
                BackIcon(
                    onClick = onNavigationIconClick,
                    modifier = Modifier.then(modifierDisplayCutoutWindowInsets)
                )
            }
        } else {
            {}
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
        ),
        scrollBehavior = topAppBarScrollBehavior
    )
}

@Preview
@Composable
private fun SettingsToolbarPreview() {
    MoviesTheme {
        SettingsToolbar(
            modifier = Modifier.statusBarsPadding(),
            isNavigationIconVisible = true,
            onNavigationIconClick = {},
            onClick = {}
        )
    }
}