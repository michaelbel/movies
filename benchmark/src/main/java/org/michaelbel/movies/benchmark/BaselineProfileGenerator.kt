package org.michaelbel.movies.benchmark

import androidx.benchmark.macro.junit4.BaselineProfileRule
import org.junit.Rule
import org.junit.Test

/**
 * ./gradlew :benchmark:connectedCheck
 */
internal class BaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun startup() =
        baselineProfileRule.collectBaselineProfile(
            packageName = "org.michaelbel.moviemade"
        ) {
            pressHome()
            startActivityAndWait()
            device.waitForIdle()
        }
}