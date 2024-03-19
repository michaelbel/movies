@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.placeholder.material3

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.runtime.Composable
import org.michaelbel.movies.ui.placeholder.PlaceholderDefaults
import org.michaelbel.movies.ui.placeholder.PlaceholderHighlight

@Composable
expect fun PlaceholderHighlight.Companion.fade(
    animationSpec: InfiniteRepeatableSpec<Float> = PlaceholderDefaults.fadeAnimationSpec,
): PlaceholderHighlight