package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.implementation

/**
 * Startup
 *
 * @see <a href="https://d.android.com/jetpack/androidx/releases/startup">Startup</a>
 */

private const val StartupVersion = "1.1.1"

private const val Startup = "androidx.startup:startup-runtime:$StartupVersion"

fun DependencyHandler.implementationStartupDependencies() {
    implementation(Startup)
}