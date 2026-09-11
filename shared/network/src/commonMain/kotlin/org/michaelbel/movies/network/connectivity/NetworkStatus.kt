package org.michaelbel.movies.network.connectivity

sealed interface NetworkStatus {
    data object Available: NetworkStatus
    data object Unavailable: NetworkStatus
}

/** Raised when a network operation is requested while Android has no usable network. */
class OfflineNetworkException : IllegalStateException(
    "No usable network is available for this operation"
)
