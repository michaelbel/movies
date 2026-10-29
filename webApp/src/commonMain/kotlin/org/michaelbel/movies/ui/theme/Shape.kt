@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

val MoviesShapes = Shapes(
    extraLarge = RoundedCornerShape(36.0.dp)
)

val middleLargeIncreasedShape: RoundedCornerShape
    @Composable get() = RoundedCornerShape(
        topStart = MaterialTheme.shapes.largeIncreased.topStart,
        topEnd = MaterialTheme.shapes.largeIncreased.topEnd,
        bottomStart = MaterialTheme.shapes.largeIncreased.bottomStart,
        bottomEnd = MaterialTheme.shapes.largeIncreased.bottomEnd
    )
