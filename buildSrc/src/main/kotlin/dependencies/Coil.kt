@file:Suppress("SpellCheckingInspection", "unused")

package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.extensions.implementation

/**
 * Coil
 *
 * @see <a href="https://github.com/coil-kt/coil/releases">Coil</a>
 */

private const val CoilVersion = "2.1.0"

private const val Coil = "io.coil-kt:coil:$CoilVersion"
private const val CoilCompose = "io.coil-kt:coil-compose:$CoilVersion"

fun DependencyHandler.implementationCoilDependencies() {
    implementation(Coil)
    implementation(CoilCompose)
}