package org.michaelbel.movies.search.ui.common

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons

@Composable
internal fun <T> SwipeToDismiss(
    item: T,
    onDelete: (T) -> Unit,
    modifier: Modifier = Modifier,
    duration: Long = 500L,
    content: @Composable (T) -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current
    var removed by rememberSaveable { mutableStateOf(false) }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                removed = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(removed) {
        if (removed) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            delay(duration)
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !removed,
        modifier = modifier,
        exit = shrinkVertically(animationSpec = tween(durationMillis = duration.toInt()), shrinkTowards = Alignment.Top) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            enableDismissFromStartToEnd = false,
            enableDismissFromEndToStart = true,
            backgroundContent = {
                val color = if (state.dismissDirection == SwipeToDismissBoxValue.EndToStart) MaterialTheme.colorScheme.errorContainer else Color.Transparent

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = MoviesIcons.Delete,
                        contentDescription = MoviesContentDescription.None,
                        tint = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            },
            content = {
                content(item)
            }
        )
    }
}