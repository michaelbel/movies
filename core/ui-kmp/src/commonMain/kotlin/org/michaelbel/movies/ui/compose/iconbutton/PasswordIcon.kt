@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.compose.iconbutton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun PasswordIcon(
    state: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
)