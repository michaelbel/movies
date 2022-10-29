package org.michaelbel.movies.network.connectivity.ktx

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.network.connectivity.NetworkStatus

inline fun <Result> Flow<NetworkStatus>.map(
    crossinline onUnavailable: suspend () -> Result,
    crossinline onAvailable: suspend () -> Result,
): Flow<Result> = map { status ->
    when (status) {
        NetworkStatus.Unavailable -> onUnavailable()
        NetworkStatus.Available -> onAvailable()
    }
}

inline fun <Result> Flow<NetworkStatus>.flatMap(
    crossinline onUnavailable: suspend () -> Flow<Result>,
    crossinline onAvailable: suspend () -> Flow<Result>,
): Flow<Result> = flatMapConcat { status ->
    when (status) {
        NetworkStatus.Unavailable -> onUnavailable()
        NetworkStatus.Available -> onAvailable()
    }
}