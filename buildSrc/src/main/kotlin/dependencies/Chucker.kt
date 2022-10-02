package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.debugImplementation
import org.michaelbel.moviemade.ktx.releaseImplementation

/**
 * Chucker
 *
 * @see <a href="https://github.com/ChuckerTeam/chucker">Chucker</a>
 */

private const val ChuckerVersion = "3.5.2"

private const val Chucker = "com.github.chuckerteam.chucker:library:$ChuckerVersion"
private const val ChuckerNoOp = "com.github.chuckerteam.chucker:library-no-op:$ChuckerVersion"

fun DependencyHandler.implementationChuckerDependencies() {
    debugImplementation(Chucker)
    releaseImplementation(ChuckerNoOp)
}