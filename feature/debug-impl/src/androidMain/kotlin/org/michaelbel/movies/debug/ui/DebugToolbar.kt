@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.debug.ui

import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.debug_impl.R
import org.michaelbel.movies.ui.compose.iconbutton.CloseIcon
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets

@Composable
internal fun DebugToolbar(
    onNavigationIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
) {
    LargeTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.debug_title),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        navigationIcon = {
            CloseIcon(
                onClick = onNavigationIconClick,
                modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
        ),
        scrollBehavior = topAppBarScrollBehavior
    )
}