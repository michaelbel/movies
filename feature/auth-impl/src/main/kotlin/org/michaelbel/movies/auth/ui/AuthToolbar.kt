package org.michaelbel.movies.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import org.michaelbel.movies.auth_impl.R
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun AuthToolbar(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.auth_title),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
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
                Image(
                    imageVector = MoviesIcons.Close,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
@DevicePreviews
private fun AuthToolbarPreview() {
    MoviesTheme {
        AuthToolbar(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer),
            onNavigationIconClick = {}
        )
    }
}