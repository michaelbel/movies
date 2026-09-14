package org.michaelbel.movies.connectivity

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.michaelbel.movies.network.connectivity.ConnectivityFallbackPolicy
import org.michaelbel.movies.network.connectivity.ConnectivityTierResult

class ConnectivityFallbackPolicyTest {
    private val policy = ConnectivityFallbackPolicy()

    @Test
    fun generalProbeRunsAfterTheRealBackendOperationFails() {
        var generalCalls = 0

        val result = policy.diagnose(
            failedTarget = "api.themoviedb.org",
            generalProbe = {
                generalCalls++
                ConnectivityTierResult(true, "https://www.google.com/generate_204", emptyList())
            }
        )

        assertTrue(generalCalls == 1)
        assertFalse(result.applicationTier.reachable)
        assertTrue(result.usedGeneralFallback)
        assertTrue(result.generalTier?.reachable == true)
        assertTrue(result.reachable)
        assertTrue(result.applicationTier.attemptedEndpoints == listOf("api.themoviedb.org"))
    }

    @Test
    fun generalProbeReportsGeneralInternetFailure() {
        var generalCalls = 0

        val result = policy.diagnose(
            failedTarget = "api.themoviedb.org",
            generalProbe = {
                generalCalls++
                ConnectivityTierResult(
                    reachable = false,
                    reachedEndpoint = null,
                    attemptedEndpoints = listOf("dns://system/example.com")
                )
            }
        )

        assertTrue(generalCalls == 1)
        assertFalse(result.applicationTier.reachable)
        assertTrue(result.usedGeneralFallback)
        assertFalse(result.generalTier?.reachable == true)
        assertFalse(result.reachable)
    }
}
