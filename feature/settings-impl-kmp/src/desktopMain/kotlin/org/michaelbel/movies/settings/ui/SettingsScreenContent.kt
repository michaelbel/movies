@file:OptIn(ExperimentalResourceApi::class)

package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import movies.feature.settings_impl_kmp.generated.resources.Res
import movies.feature.settings_impl_kmp.generated.resources.settings_github
import movies.feature.settings_impl_kmp.generated.resources.settings_github_description
import movies.feature.settings_impl_kmp.generated.resources.settings_language
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.MOVIES_GITHUB_URL
import org.michaelbel.movies.common.browser.openUrlDesktop
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.settings.ui.common.SettingItem
import org.michaelbel.movies.settings.ui.common.SettingsDialog
import org.michaelbel.movies.ui.icons.MoviesIcons

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsScreenContent(
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun SettingsScreenContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = modifier,
        topBar = {
            SettingsToolbar(
                modifier = Modifier.fillMaxWidth(),
                onNavigationIconClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->
        LazyColumn(
            state = lazyListState,
            contentPadding = innerPadding
        ) {
            item {
                var languageDialog by remember { mutableStateOf(false) }

                if (languageDialog) {
                    SettingsDialog(
                        icon = MoviesIcons.Language,
                        title = stringResource(Res.string.settings_language),
                        items = AppLanguage.VALUES,
                        currentItem = AppLanguage.English,
                        onItemSelect = {},
                        onDismissRequest = { languageDialog = false }
                    )
                }

                SettingItem(
                    title = stringResource(Res.string.settings_language),
                    description = "English",
                    icon = MoviesIcons.Language,
                    onClick = { languageDialog = true }
                )
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    thickness = .1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            item {
                SettingItem(
                    title = stringResource(Res.string.settings_github),
                    description = stringResource(Res.string.settings_github_description),
                    icon = MoviesIcons.Github,
                    onClick = { openUrlDesktop(MOVIES_GITHUB_URL) }
                )
            }
        }
    }
}