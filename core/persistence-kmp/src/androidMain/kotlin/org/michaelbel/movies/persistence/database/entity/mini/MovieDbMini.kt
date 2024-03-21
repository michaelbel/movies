@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database.entity.mini

actual data class MovieDbMini(
    val movieList: String,
    val id: Int,
    val title: String,
)