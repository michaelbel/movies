package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import org.michaelbel.movies.ui.compose.iconbutton.SearchIcon
import org.michaelbel.movies.ui.compose.iconbutton.SettingsIcon
import org.michaelbel.movies.ui.icons.MoviesIcons

@Composable
internal fun FeedToolbar(
    title: String,
    onSearchIconClick: () -> Unit,
    onAuthIconClick: () -> Unit,
    onAccountIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
                )
            },
            modifier = modifier,
            navigationIcon = {
                SearchIcon(
                    onClick = onSearchIconClick
                )
            },
            actions = {
                Row {
                    SettingsIcon(
                        onClick = onSettingsIconClick
                    )

                    IconButton(
                        onClick = if (true) onAuthIconClick else onAccountIconClick
                    ) {
                        if (true) {
                            Image(
                                imageVector = MoviesIcons.AccountCircle,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                            )
                        } else {
                            /*AccountAvatar(
                                account = account,
                                fontSize = account.lettersTextFontSizeSmall,
                                modifier = Modifier.size(32.dp)
                            )*/
                        }
                    }
                }
            }
        )

        /*if (isTmdbApiKeyEmpty) {
            ApiKeyBox(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )
        }*/
    }
}