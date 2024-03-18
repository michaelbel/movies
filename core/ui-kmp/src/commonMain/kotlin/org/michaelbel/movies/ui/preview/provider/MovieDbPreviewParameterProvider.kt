@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.persistence.database.entity.MovieDb

expect class MovieDbPreviewParameterProvider: PreviewParameterProvider<MovieDb>