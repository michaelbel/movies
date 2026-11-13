package org.michaelbel.movies.network.connectivity

data class ConnectivityTierResult(
    val reachable: Boolean,
    val reachedEndpoint: String?,
    val attemptedEndpoints: List<String>
)

data class ConnectivityFallbackResult(
    val applicationTier: ConnectivityTierResult,
    val generalTier: ConnectivityTierResult?
) {
    val reachable: Boolean
        get() = applicationTier.reachable || generalTier?.reachable == true

    val usedGeneralFallback: Boolean
        get() = generalTier != null
}

/**
 * Encodes the post-failure diagnostic invariant independently of Android and networking APIs:
 * the failed real target is recorded, then one generic active Internet diagnostic runs.
 */
class ConnectivityFallbackPolicy {
    fun diagnose(
        failedTarget: String,
        generalProbe: () -> ConnectivityTierResult
    ): ConnectivityFallbackResult {
        return ConnectivityFallbackResult(
            applicationTier = ConnectivityTierResult(
                reachable = false,
                reachedEndpoint = null,
                attemptedEndpoints = listOf(failedTarget)
            ),
            generalTier = generalProbe()
        )
    }
}
