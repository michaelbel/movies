@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.network.ktx

import org.michaelbel.movies.network.model.Result

expect val <T> Result<T>.nextPage: Int

expect val <T> Result<T>.isPaginationReached: Boolean

expect val <T> Result<T>.isEmpty: Boolean