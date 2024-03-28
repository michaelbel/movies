package org.michaelbel.movies.network.connectivity

sealed interface NetworkStatus {
    data object Available: NetworkStatus
    data object Unavailable: NetworkStatus
}