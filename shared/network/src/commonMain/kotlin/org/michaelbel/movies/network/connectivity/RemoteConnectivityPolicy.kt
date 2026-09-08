package org.michaelbel.movies.network.connectivity

/**
 * Policy for starting a new remote operation.
 *
 * Android's connected signal can be positive for a VPN-only network, so both the
 * generic connected state and a real Wi-Fi/cellular/Ethernet transport are required.
 */
object RemoteConnectivityPolicy {
    fun canStartRemoteRequest(
        isConnected: Boolean,
        hasPhysicalNetwork: Boolean
    ): Boolean = isConnected && hasPhysicalNetwork
}
