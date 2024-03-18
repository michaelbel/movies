@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.lifecycle

import androidx.compose.runtime.Composable

@Composable
expect fun OnResume(
    onResume: () -> Unit
)