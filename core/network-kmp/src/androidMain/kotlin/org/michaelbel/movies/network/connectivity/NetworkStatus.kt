@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.network.connectivity

actual sealed interface NetworkStatus {
    data object Available: NetworkStatus
    data object Unavailable: NetworkStatus
}