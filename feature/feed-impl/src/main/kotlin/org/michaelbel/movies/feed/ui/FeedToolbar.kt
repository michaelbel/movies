package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.michaelbel.movies.feed_impl.R
import org.michaelbel.movies.ui.icon.MoviesIcons
import org.michaelbel.movies.ui.preview.BooleanPreviewParameterProvider
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun FeedToolbar(
    modifier: Modifier = Modifier,
    isSettingsIconVisible: Boolean,
    onSettingsIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.feed_title),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = modifier,
        actions = {
            if (isSettingsIconVisible) {
                IconButton(
                    onClick = onSettingsIconClick
                ) {
                    Image(
                        imageVector = MoviesIcons.Settings,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.inversePrimary
        )
    )
}

@Composable
@DevicePreviews
private fun FeedToolbarPreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) visible: Boolean
) {
    MoviesTheme {
        FeedToolbar(
            modifier = Modifier
                .statusBarsPadding(),
            isSettingsIconVisible = visible,
            onSettingsIconClick = {}
        )
    }
}