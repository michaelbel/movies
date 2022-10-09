package org.michaelbel.movies.feed.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.feed.R
import org.michaelbel.movies.ui.theme.MoviesTheme
import org.michaelbel.movies.ui.icon.MoviesIcons

@Composable
internal fun FeedToolbar(
    modifier: Modifier = Modifier,
    isSettingsIconVisible: Boolean,
    onNavigationIconClick: () -> Unit
) {
    SmallTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.feed_title),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        modifier = modifier,
        actions = {
            if (isSettingsIconVisible) {
                IconButton(
                    onClick = onNavigationIconClick
                ) {
                    Image(
                        imageVector = MoviesIcons.Settings,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                }
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FeedToolbarPreview() {
    MoviesTheme {
        FeedToolbar(
            modifier = Modifier
                .statusBarsPadding(),
            isSettingsIconVisible = true,
            onNavigationIconClick = {}
        )
    }
}