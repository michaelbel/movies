package org.michaelbel.movies.settings.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.settings.R
import org.michaelbel.movies.ui.theme.MoviesTheme
import org.michaelbel.movies.ui.icon.MoviesIcons

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
                maxLines = 1
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsToolbarPreview() {
    MoviesTheme {
        SettingsToolbar(
            modifier = Modifier
                .statusBarsPadding(),
            onNavigationIconClick = {}
        )
    }
}