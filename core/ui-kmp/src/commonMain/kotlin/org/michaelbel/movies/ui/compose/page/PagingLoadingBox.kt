@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.compose.page

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun PagingLoadingBox(
    modifier: Modifier = Modifier
)