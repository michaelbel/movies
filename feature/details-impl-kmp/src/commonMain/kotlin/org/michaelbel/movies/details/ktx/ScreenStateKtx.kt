@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.details.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.michaelbel.movies.network.ScreenState
import org.michaelbel.movies.persistence.database.entity.MovieDb

expect val ScreenState.Content<*>.movie: MovieDb

expect val ScreenState.toolbarTitle: String

expect val ScreenState.movieUrl: String?

@Composable
expect fun ScreenState.primaryContainer(isAmoledTheme: Boolean): Color

@Composable
expect fun ScreenState.onPrimaryContainer(isAmoledTheme: Boolean): Color

@Composable
expect fun ScreenState.scrolledContainerColor(isAmoledTheme: Boolean): Color