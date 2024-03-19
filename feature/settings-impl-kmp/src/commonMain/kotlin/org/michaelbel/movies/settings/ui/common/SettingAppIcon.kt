@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.settings.ui.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.ui.appicon.IconAlias

@Composable
expect fun RowScope.SettingAppIcon(
    iconAlias: IconAlias,
    onClick: (IconAlias) -> Unit,
    modifier: Modifier = Modifier
)