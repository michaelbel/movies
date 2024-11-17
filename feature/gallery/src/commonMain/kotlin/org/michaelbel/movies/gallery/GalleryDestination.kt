package org.michaelbel.movies.gallery

import kotlinx.serialization.Serializable
import org.michaelbel.movies.persistence.database.typealiases.MovieId

@Serializable
internal class GalleryDestination(
    val movieId: MovieId
)