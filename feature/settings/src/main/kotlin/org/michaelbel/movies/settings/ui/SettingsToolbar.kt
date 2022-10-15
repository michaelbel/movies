package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import org.michaelbel.movies.settings.R
import org.michaelbel.movies.ui.icon.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SettingsToolbar(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit
) {
    SmallTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.settings_title),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onNavigationIconClick
            ) {
                Icon(
                    imageVector = MoviesIcons.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
@DevicePreviews
private fun SettingsToolbarPreview() {
    MoviesTheme {
        SettingsToolbar(
            modifier = Modifier
                .statusBarsPadding(),
            onNavigationIconClick = {}
        )
    }
}