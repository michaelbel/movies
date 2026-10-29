package org.michaelbel.movies.ui.placeholder

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.util.lerp
import kotlin.math.max

@Stable
interface PlaceholderHighlight {
    val animationSpec: InfiniteRepeatableSpec<Float>?

    fun brush(progress: Float, size: Size): Brush

    fun alpha(progress: Float): Float

    companion object
}

fun PlaceholderHighlight.Companion.fade(
    highlightColor: Color,
    animationSpec: InfiniteRepeatableSpec<Float> = PlaceholderDefaults.fadeAnimationSpec
): PlaceholderHighlight = Fade(
    highlightColor = highlightColor,
    animationSpec = animationSpec,
)

fun PlaceholderHighlight.Companion.shimmer(
    highlightColor: Color,
    animationSpec: InfiniteRepeatableSpec<Float> = PlaceholderDefaults.shimmerAnimationSpec,
    progressForMaxAlpha: Float = 0.6F
): PlaceholderHighlight = Shimmer(
    highlightColor = highlightColor,
    animationSpec = animationSpec,
    progressForMaxAlpha = progressForMaxAlpha,
)

private data class Fade(
    private val highlightColor: Color,
    override val animationSpec: InfiniteRepeatableSpec<Float>,
): PlaceholderHighlight {
    private val brush = SolidColor(highlightColor)
    override fun brush(progress: Float, size: Size): Brush = brush
    override fun alpha(progress: Float): Float = progress
}

private data class Shimmer(
    private val highlightColor: Color,
    override val animationSpec: InfiniteRepeatableSpec<Float>,
    private val progressForMaxAlpha: Float = 0.6F,
): PlaceholderHighlight {
    override fun brush(progress: Float, size: Size): Brush = Brush.radialGradient(
        colors = listOf(
            highlightColor.copy(alpha = 0F),
            highlightColor,
            highlightColor.copy(alpha = 0F),
        ),
        center = Offset(x = 0F, y = 0F),
        radius = (max(size.width, size.height) * progress * 2).coerceAtLeast(0.01F),
    )

    override fun alpha(progress: Float): Float = when {
        progress <= progressForMaxAlpha -> lerp(0F, 1F, progress / progressForMaxAlpha)
        else -> lerp(1F, 0F, (progress - progressForMaxAlpha) / (1F - progressForMaxAlpha))
    }
}
