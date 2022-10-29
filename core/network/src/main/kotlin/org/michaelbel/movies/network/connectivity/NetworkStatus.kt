package org.michaelbel.movies.network.connectivity

sealed interface NetworkStatus {
    object Available: NetworkStatus
    object Unavailable: NetworkStatus
}