@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.common.version.AppVersionData

@Composable
expect fun SettingsVersionBox(
    appVersionData: AppVersionData,
    modifier: Modifier = Modifier
)