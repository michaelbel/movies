package org.michaelbel.movies.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import org.michaelbel.movies.ui.icons.MoviesIcons

@Composable
actual fun PasswordVisibilityIcon(
    passwordVisible: Boolean,
    contentDescription: String,
    modifier: Modifier,
    tint: Color
) {
    Image(
        imageVector = if (passwordVisible) MoviesIcons.Visibility else MoviesIcons.VisibilityOff,
        contentDescription = contentDescription,
        modifier = modifier,
        colorFilter = ColorFilter.tint(tint)
    )
}
