package org.michaelbel.movies.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun AuthLinksBox(
    onTermsOfUseClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(
            thickness = .1.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(MoviesStrings.auth_terms_of_use),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickableWithoutRipple { onTermsOfUseClick() },
                style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(3.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
            )

            Text(
                text = stringResource(MoviesStrings.auth_privacy_policy),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickableWithoutRipple { onPrivacyPolicyClick() },
                style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }
}

@Composable
/*@DevicePreviews*/
private fun AuthLinksBoxPreview() {
    MoviesTheme {
        AuthLinksBox(
            onTermsOfUseClick = {},
            onPrivacyPolicyClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
/*@Preview*/
private fun AuthLinksBoxAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        AuthLinksBox(
            onTermsOfUseClick = {},
            onPrivacyPolicyClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}