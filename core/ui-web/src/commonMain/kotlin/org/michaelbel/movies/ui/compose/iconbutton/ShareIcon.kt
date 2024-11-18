package org.michaelbel.movies.ui.compose.iconbutton

import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.icons.MoviesIcons

@Composable
fun ShareIcon(
    url: String,
    onShareClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    onContainerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    IconButton(
        onClick = { onShareClick(url) },
        modifier = modifier
    ) {
        Image(
            imageVector = MoviesIcons.Share,
            contentDescription = stringResource(MoviesContentDescriptionCommon.ShareIcon),
            colorFilter = ColorFilter.tint(onContainerColor)
        )
    }
}