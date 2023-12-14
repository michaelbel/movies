package org.michaelbel.movies.ui.compose.iconbutton

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.BooleanPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun PasswordIcon(
    state: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (state) MoviesIcons.Visibility else MoviesIcons.VisibilityOff,
            contentDescription = stringResource(if (state) MoviesContentDescription.PasswordIcon else MoviesContentDescription.PasswordOffIcon)
        )
    }
}

@Composable
@DevicePreviews
private fun PasswordIconPreview(
    @PreviewParameter(BooleanPreviewParameterProvider::class) state: Boolean
) {
    MoviesTheme {
        PasswordIcon(
            state = state,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}