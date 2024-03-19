@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.details.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import org.michaelbel.movies.persistence.database.entity.MovieDb

@Composable
expect fun DetailsContent(
    movie: MovieDb,
    onNavigateToGallery: (Int) -> Unit,
    onGenerateColors: (Int, Palette) -> Unit,
    modifier: Modifier = Modifier,
    isThemeAmoled: Boolean = false,
    onContainerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    placeholder: Boolean = false
)