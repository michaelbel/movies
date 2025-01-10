package org.michaelbel.movies.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SwitchCheckIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = MoviesIcons.Check,
        contentDescription = MoviesContentDescriptionCommon.None,
        modifier = modifier.size(SwitchDefaults.IconSize),
    )
}

@Preview
@Composable
private fun SwitchCheckIconPreview() {
    MoviesTheme {
        SwitchCheckIcon(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}