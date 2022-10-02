package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api

/**
 * Coil
 *
 * @see <a href="https://github.com/coil-kt/coil/releases">Coil</a>
 */

private const val CoilVersion = "2.2.1"

private const val Coil = "io.coil-kt:coil:$CoilVersion"
private const val CoilCompose = "io.coil-kt:coil-compose:$CoilVersion"

fun DependencyHandler.apiCoilDependencies() {
    api(Coil)
    api(CoilCompose)
}