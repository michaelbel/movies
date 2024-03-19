@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.settings.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
expect fun SettingSwitchItem(
    title: String,
    description: String,
    icon: ImageVector,
    checked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
)

@Composable
expect fun SettingSwitchItem(
    title: String,
    description: String,
    icon: Painter,
    checked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
)