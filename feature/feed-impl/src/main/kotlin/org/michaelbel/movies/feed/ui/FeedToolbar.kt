package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.domain.data.entity.AccountDb
import org.michaelbel.movies.domain.data.ktx.isEmpty
import org.michaelbel.movies.feed_impl.R
import org.michaelbel.movies.ui.compose.AccountAvatar
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.lettersTextFontSizeSmall
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.BooleanPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun FeedToolbar(
    account: AccountDb,
    isUpdateIconVisible: Boolean,
    isSettingsIconVisible: Boolean,
    onAuthIconClick: () -> Unit,
    onAccountIconClick: () -> Unit,
    onUpdateIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.feed_title),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = modifier,
        actions = {
            if (isUpdateIconVisible) {
                IconButton(
                    onClick = onUpdateIconClick
                ) {
                    Image(
                        imageVector = MoviesIcons.SystemUpdate,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                }
            }

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

            IconButton(
                onClick = if (account.isEmpty) onAuthIconClick else onAccountIconClick
            ) {
                if (account.isEmpty) {
                    Image(
                        imageVector = MoviesIcons.Account,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                } else {
                    AccountAvatar(
                        account = account,
                        fontSize = account.lettersTextFontSizeSmall,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
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
            account = AccountDb.Empty,
            isUpdateIconVisible = true,
            isSettingsIconVisible = visible,
            onAccountIconClick = {},
            onAuthIconClick = {},
            onUpdateIconClick = {},
            onSettingsIconClick = {},
            modifier = Modifier.statusBarsPadding()
        )
    }
}