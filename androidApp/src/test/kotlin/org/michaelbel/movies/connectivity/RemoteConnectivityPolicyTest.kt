package org.michaelbel.movies.connectivity

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.michaelbel.movies.network.connectivity.RemoteConnectivityPolicy

class RemoteConnectivityPolicyTest {
    @Test
    fun normalNetworkAllowsRemoteOperation() {
        assertTrue(RemoteConnectivityPolicy.canStartRemoteRequest(true, true))
    }

    @Test
    fun vpnOverPhysicalNetworkAllowsRemoteOperation() {
        assertTrue(RemoteConnectivityPolicy.canStartRemoteRequest(true, true))
    }

    @Test
    fun vpnOnlyNetworkIsOfflineEvenWhenConnectedSignalIsTrue() {
        assertFalse(RemoteConnectivityPolicy.canStartRemoteRequest(true, false))
    }

    @Test
    fun recoveryAllowsRemoteOperationAgain() {
        assertTrue(RemoteConnectivityPolicy.canStartRemoteRequest(true, true))
    }

    @Test
    fun noNetworkIsOffline() {
        assertFalse(RemoteConnectivityPolicy.canStartRemoteRequest(false, false))
    }

    @Test
    fun unvalidatedPhysicalNetworkIsNotBlockedByValidationSignal() {
        assertTrue(RemoteConnectivityPolicy.canStartRemoteRequest(true, true))
    }
}
