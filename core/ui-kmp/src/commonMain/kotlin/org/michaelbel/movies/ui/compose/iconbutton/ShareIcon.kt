@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.compose.iconbutton

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
expect fun ShareIcon(
    url: String,
    modifier: Modifier = Modifier,
    onContainerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
)