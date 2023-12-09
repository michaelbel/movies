package org.michaelbel.movies.ui

import org.michaelbel.movies.persistence.database.entity.MovieDb

sealed interface FeedElement {

    data class Content(
        val movieDb: MovieDb
    ): FeedElement

    data object Ad: FeedElement
}