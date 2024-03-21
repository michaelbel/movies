@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.entity.MovieDb

expect fun MovieResponse.movieDb(
    movieList: String,
    position: Int
): MovieDb