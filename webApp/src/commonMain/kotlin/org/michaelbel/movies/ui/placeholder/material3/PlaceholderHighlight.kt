package org.michaelbel.movies.ui.placeholder.material3

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.michaelbel.movies.ui.placeholder.PlaceholderDefaults
import org.michaelbel.movies.ui.placeholder.PlaceholderHighlight
import org.michaelbel.movies.ui.placeholder.fade
import org.michaelbel.movies.ui.placeholder.shimmer

@Composable
fun PlaceholderHighlight.Companion.fade(
    animationSpec: InfiniteRepeatableSpec<Float> = PlaceholderDefaults.fadeAnimationSpec
) = PlaceholderHighlight.fade(
    highlightColor = PlaceholderDefaults.fadeHighlightColor(),
    animationSpec = animationSpec,
)

@Composable
fun PlaceholderHighlight.Companion.shimmer(
    animationSpec: InfiniteRepeatableSpec<Float> = PlaceholderDefaults.shimmerAnimationSpec,
    progressForMaxAlpha: Float = 0.6F
): PlaceholderHighlight = PlaceholderHighlight.shimmer(
    highlightColor = PlaceholderDefaults.shimmerHighlightColor(),
    animationSpec = animationSpec,
    progressForMaxAlpha = progressForMaxAlpha,
)

@Composable
fun PlaceholderDefaults.fadeHighlightColor(
    backgroundColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.surface,
    alpha: Float = 0.3F
): androidx.compose.ui.graphics.Color = backgroundColor.copy(alpha = alpha)

@Composable
fun PlaceholderDefaults.shimmerHighlightColor(
    backgroundColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.inverseSurface,
    alpha: Float = 0.75F
): androidx.compose.ui.graphics.Color = backgroundColor.copy(alpha = alpha)
