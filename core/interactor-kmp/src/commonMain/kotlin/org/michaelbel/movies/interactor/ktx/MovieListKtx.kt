@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.interactor.ktx

import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.network.isTmdbApiKeyEmpty
import org.michaelbel.movies.persistence.database.entity.MovieDb

expect val MovieList.nameOrLocalList: String