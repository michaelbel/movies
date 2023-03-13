package org.michaelbel.movies.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun AccountCountryBox(
    modifier: Modifier = Modifier,
    country: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = MoviesIcons.LocationOn,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Text(
            text = country,
            modifier = Modifier
                .padding(
                    start = 4.dp
                ),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
@DevicePreviews
private fun AccountCountryBoxPreview() {
    MoviesTheme {
        AccountCountryBox(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer),
            country = "Finland"
        )
    }
}