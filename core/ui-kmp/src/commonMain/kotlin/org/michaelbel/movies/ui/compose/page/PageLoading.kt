@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.compose.page

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.common.appearance.FeedView

@Composable
expect fun PageLoading(
    feedView: FeedView,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
)