package org.michaelbel.movies.feed.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue.EndToStart
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.michaelbel.movies.ui.icons.MoviesIcons
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun <T> SwipeToDismiss(
    item: T,
    onDelete: (T) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    duration: Long = 500L,
    content: @Composable (T, (T) -> Unit) -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current
    var removed by rememberSaveable { mutableStateOf(false) }
    val state = rememberSwipeToDismissBoxState()
    val swipeProgress = if (state.dismissDirection == EndToStart) state.progress.coerceIn(0F, 1F) else 0F
    val iconScale by animateFloatAsState(
        targetValue = if (swipeProgress > 0F) .75F + swipeProgress * .45F else .6F,
        animationSpec = spring(
            dampingRatio = .52F,
            stiffness = 300F
        ),
        label = "swipeDeleteIconScale"
    )
    val iconAlpha by animateFloatAsState(
        targetValue = if (swipeProgress > 0F) .2F + swipeProgress * .8F else 0F,
        animationSpec = tween(durationMillis = 120),
        label = "swipeDeleteIconAlpha"
    )
    LaunchedEffect(state.currentValue, removed) {
        if (!removed && state.currentValue == EndToStart) {
            removed = true
        }
    }

    LaunchedEffect(removed) {
        if (removed) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            delay(duration.milliseconds)
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !removed,
        modifier = modifier,
        exit = shrinkVertically(animationSpec = tween(durationMillis = duration.toInt()), shrinkTowards = Alignment.Top) + fadeOut()
    ) {
        Box(
            modifier = Modifier.clip(shape)
        ) {
            SwipeToDismissBox(
                state = state,
                enableDismissFromStartToEnd = false,
                enableDismissFromEndToStart = true,
                backgroundContent = {
                    val color = if (swipeProgress > 0F) MaterialTheme.colorScheme.errorContainer else Color.Transparent

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = MoviesIcons.Delete,
                            contentDescription = null,
                            modifier = Modifier.graphicsLayer {
                                alpha = iconAlpha
                                scaleX = iconScale
                                scaleY = iconScale
                            },
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                },
                content = {
                    content(item, onDelete)
                }
            )
        }
    }
}
