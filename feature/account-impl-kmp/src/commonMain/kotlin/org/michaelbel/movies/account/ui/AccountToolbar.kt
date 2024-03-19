@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.account.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun AccountToolbar(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit
)