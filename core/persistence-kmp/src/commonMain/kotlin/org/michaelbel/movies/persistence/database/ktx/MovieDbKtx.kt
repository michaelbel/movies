@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.persistence.database.entity.MovieDb

expect val MovieDb.isNotEmpty: Boolean

expect val MovieDb.url: String

expect val MovieDb?.orEmpty: MovieDb