package org.michaelbel.movies.settings.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.settings.ktx.themeTextRes
import org.michaelbel.movies.ui.MoviesTheme
import org.michaelbel.movies.ui.SystemTheme

@Composable
fun SettingsThemeModalContent(
    modifier: Modifier = Modifier,
    themes: List<SystemTheme>,
    currentTheme: SystemTheme,
    onThemeSelected: (SystemTheme) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        themes.forEach { systemTheme: SystemTheme ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        onThemeSelected(systemTheme)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentTheme == systemTheme,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)
                    ),
                    modifier = Modifier
                        .padding(
                            start = 16.dp
                        )
                )

                Text(
                    text = stringResource(systemTheme.themeTextRes),
                    modifier = Modifier
                        .padding(
                            start = 8.dp
                        ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsThemeModalContentPreview() {
    MoviesTheme {
        SettingsThemeModalContent(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            themes = listOf(
                SystemTheme.NightNo,
                SystemTheme.NightYes,
                SystemTheme.FollowSystem
            ),
            currentTheme = SystemTheme.FollowSystem,
            onThemeSelected = {}
        )
    }
}