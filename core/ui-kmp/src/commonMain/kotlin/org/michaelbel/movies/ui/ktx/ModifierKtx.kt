@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.ktx

import androidx.compose.ui.Modifier

expect fun Modifier.clickableWithoutRipple(
    block: () -> Unit
): Modifier