package org.michaelbel.movies.details.ktx

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.details_impl_kmp.R
import org.michaelbel.movies.network.ScreenState
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.ktx.url

actual val ScreenState.Content<*>.movie: MovieDb
    get() = data as MovieDb

actual val ScreenState.toolbarTitle: String
    @Composable get() = when (this) {
        is ScreenState.Loading -> stringResource(R.string.details_title)
        is ScreenState.Content<*> -> movie.title
        is ScreenState.Failure -> stringResource(R.string.details_title)
    }

actual val ScreenState.movieUrl: String?
    get() = if (this is ScreenState.Content<*>) movie.url else null

@Composable
actual fun ScreenState.primaryContainer(isAmoledTheme: Boolean): Color {
    return when (this) {
        is ScreenState.Loading -> MaterialTheme.colorScheme.primaryContainer
        is ScreenState.Content<*> -> if (movie.containerColor != null && !isAmoledTheme) Color(requireNotNull(movie.containerColor)) else MaterialTheme.colorScheme.primaryContainer
        is ScreenState.Failure -> MaterialTheme.colorScheme.primaryContainer
    }
}

@Composable
actual fun ScreenState.onPrimaryContainer(isAmoledTheme: Boolean): Color {
    return when (this) {
        is ScreenState.Loading -> MaterialTheme.colorScheme.onPrimaryContainer
        is ScreenState.Content<*> -> if (movie.onContainerColor != null && !isAmoledTheme) Color(requireNotNull(movie.onContainerColor)) else MaterialTheme.colorScheme.onPrimaryContainer
        is ScreenState.Failure -> MaterialTheme.colorScheme.onPrimaryContainer
    }
}

@Composable
actual fun ScreenState.scrolledContainerColor(isAmoledTheme: Boolean): Color {
    return when (this) {
        is ScreenState.Loading -> MaterialTheme.colorScheme.inversePrimary
        is ScreenState.Content<*> -> if (movie.containerColor != null && !isAmoledTheme) Color.Transparent else MaterialTheme.colorScheme.inversePrimary
        is ScreenState.Failure -> MaterialTheme.colorScheme.inversePrimary
    }
}