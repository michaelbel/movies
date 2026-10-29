@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.ui.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.toPath
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.graphics.shapes.Morph

@Composable
fun RotatingCookie12SidedBox(
    color: Color,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit
) {
    val shapeSequence = remember {
        listOf(
            MaterialShapes.Cookie12Sided,
            MaterialShapes.Cookie9Sided,
            MaterialShapes.Cookie7Sided,
            MaterialShapes.Cookie6Sided,
            MaterialShapes.Cookie4Sided,
            MaterialShapes.Cookie6Sided,
            MaterialShapes.Cookie7Sided,
            MaterialShapes.Cookie9Sided,
            MaterialShapes.Cookie12Sided
        )
    }
    val morphs = remember(shapeSequence) {
        shapeSequence.zipWithNext { start, end ->
            Morph(start = start, end = end)
        }
    }
    val infiniteTransition = rememberInfiniteTransition(label = "rotating_cookie_shape")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8_000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotating_cookie_shape_rotation"
    )
    val progress by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = morphs.size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8_000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotating_cookie_shape_morph"
    )
    val cycleProgress = when {
        progress >= morphs.size -> 0F
        else -> progress
    }
    val morphIndex = cycleProgress.toInt()
    val morphFraction = cycleProgress - morphIndex
    val path = remember { Path() }

    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            path.reset()
            morphs[morphIndex].toPath(progress = morphFraction, path = path)
            path.transform(Matrix().apply { scale(x = size.width, y = size.height) })
            path.translate(size.center - path.getBounds().center)

            rotate(degrees = rotation, pivot = center) {
                drawPath(path = path, color = color)
            }
        }

        content()
    }
}
