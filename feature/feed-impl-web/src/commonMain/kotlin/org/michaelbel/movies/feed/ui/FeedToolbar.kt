@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.ui.compose.ApiKeyBox
import org.michaelbel.movies.ui.compose.iconbutton.SearchIcon
import org.michaelbel.movies.ui.compose.iconbutton.SettingsIcon
import org.michaelbel.movies.ui.ktx.modifierDisplayCutoutWindowInsets
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun FeedToolbar(
    title: String,
    isTmdbApiKeyEmpty: Boolean,
    isSearchIconVisible: Boolean,
    onSearchIconClick: () -> Unit,
    isAuthIconVisible: Boolean,
    onAuthIconClick: () -> Unit,
    onAccountIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
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
            navigationIcon = if (isSearchIconVisible) {
                {
                    SearchIcon(
                        onClick = onSearchIconClick,
                        modifier = Modifier.then(modifierDisplayCutoutWindowInsets)
                    )
                }
            } else {
                {}
            },
            actions = {
                Row(
                    modifier = Modifier.then(modifierDisplayCutoutWindowInsets)
                ) {
                    SettingsIcon(
                        onClick = onSettingsIconClick
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
            ),
            scrollBehavior = topAppBarScrollBehavior
        )

        if (isTmdbApiKeyEmpty) {
            ApiKeyBox(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun FeedToolbarPreview() {
    MoviesTheme {
        FeedToolbar(
            title = "Not Playing",
            isTmdbApiKeyEmpty = true,
            isSearchIconVisible = true,
            onSearchIconClick = {},
            onAccountIconClick = {},
            isAuthIconVisible = true,
            onAuthIconClick = {},
            onSettingsIconClick = {},
            topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            modifier = Modifier.statusBarsPadding()
        )
    }
}