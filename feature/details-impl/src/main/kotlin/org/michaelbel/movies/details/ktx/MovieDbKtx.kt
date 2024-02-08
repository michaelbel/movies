package org.michaelbel.movies.details.ktx

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.details_impl.R
import org.michaelbel.movies.persistence.database.entity.MovieDb

// fixme R&D

internal val MovieDb.toolbarTitle: String
    @Composable get() = title.ifEmpty { stringResource(R.string.details_title) }

internal val MovieDb.primaryContainer: Color
    @Composable get() = if (containerColor != null) Color(requireNotNull(containerColor)) else MaterialTheme.colorScheme.primaryContainer

internal val MovieDb.onPrimaryContainer: Color
    @Composable get() = if (onContainerColor != null) Color(requireNotNull(onContainerColor)) else MaterialTheme.colorScheme.onPrimaryContainer

internal val MovieDb.scrolledContainerColor: Color
    @Composable get() = if (containerColor != null) Color.Transparent else MaterialTheme.colorScheme.inversePrimary