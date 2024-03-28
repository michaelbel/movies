@file:OptIn(ExperimentalResourceApi::class)

package org.michaelbel.movies.account.ui

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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun AccountCountryBox(
    country: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = MoviesIcons.LocationOn,
            contentDescription = stringResource(MoviesContentDescriptionCommon.UserLocationIcon),
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Text(
            text = country,
            modifier = Modifier.padding(start = 4.dp),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

@Composable
/*@DevicePreviews*/
private fun AccountCountryBoxPreview() {
    MoviesTheme {
        AccountCountryBox(
            country = "Finland",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
/*@Preview*/
private fun AccountCountryBoxAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        AccountCountryBox(
            country = "Finland",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}