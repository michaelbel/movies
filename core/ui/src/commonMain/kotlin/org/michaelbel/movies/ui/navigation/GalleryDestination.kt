package org.michaelbel.movies.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.michaelbel.movies.persistence.database.typealiases.MovieId

@Serializable
data class GalleryDestination(
    val movieId: MovieId
) : NavKey