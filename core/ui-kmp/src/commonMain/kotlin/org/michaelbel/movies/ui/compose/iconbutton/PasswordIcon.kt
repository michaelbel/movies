@file:OptIn(ExperimentalResourceApi::class)

package org.michaelbel.movies.ui.compose.iconbutton

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.icons.MoviesIcons
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
        Image(
            imageVector = if (state) MoviesIcons.Visibility else MoviesIcons.VisibilityOff,
            contentDescription = stringResource(if (state) MoviesContentDescriptionCommon.PasswordIcon else MoviesContentDescriptionCommon.PasswordOffIcon),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

@Composable
/*@DevicePreviews*/
private fun PasswordIconPreview(
    /*@PreviewParameter(BooleanPreviewParameterProvider::class)*/ state: Boolean
) {
    MoviesTheme {
        PasswordIcon(
            state = state,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
/*@Preview*/
private fun PasswordIconAmoledPreview(
    /*@PreviewParameter(BooleanPreviewParameterProvider::class)*/ state: Boolean
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        PasswordIcon(
            state = state,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}