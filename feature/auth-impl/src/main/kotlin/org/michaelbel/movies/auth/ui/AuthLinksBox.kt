package org.michaelbel.movies.auth.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.auth_impl.R
import org.michaelbel.movies.common.browser.openUrl
import org.michaelbel.movies.entities.TMDB_PRIVACY_POLICY
import org.michaelbel.movies.entities.TMDB_TERMS_OF_USE
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun AuthLinksBox(
    modifier: Modifier = Modifier
) {
    val toolbarColor: Int = MaterialTheme.colorScheme.primary.toArgb()

    val resultContract = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            thickness = 0.5.dp
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.auth_terms_of_use),
                modifier = Modifier
                    .padding(
                        vertical = 16.dp
                    )
                    .clickableWithoutRipple {
                        openUrl(resultContract, toolbarColor, TMDB_TERMS_OF_USE)
                    },
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium
            )

            Box(
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp
                    )
                    .size(3.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )

            Text(
                text = stringResource(R.string.auth_privacy_policy),
                modifier = Modifier
                    .padding(
                        vertical = 16.dp
                    )
                    .clickableWithoutRipple {
                        openUrl(resultContract, toolbarColor, TMDB_PRIVACY_POLICY)
                    },
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
@DevicePreviews
private fun AuthLinksBoxPreview() {
    MoviesTheme {
        AuthLinksBox(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}