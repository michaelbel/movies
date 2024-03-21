@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.network.ktx

import org.michaelbel.movies.network.config.ScreenState

expect val ScreenState.isFailure: Boolean

expect val ScreenState.throwable: Throwable