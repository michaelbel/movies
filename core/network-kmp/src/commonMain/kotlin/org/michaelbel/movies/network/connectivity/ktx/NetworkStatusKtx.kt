@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.network.connectivity.ktx

import org.michaelbel.movies.network.connectivity.NetworkStatus

expect val NetworkStatus.isAvailable: Boolean