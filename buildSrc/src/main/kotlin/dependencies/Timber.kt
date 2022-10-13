package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api

/**
 * Timber
 *
 * @see <a href="https://github.com/JakeWharton/timber/releases">Timber</a>
 */

private const val TimberVersion = "5.0.1"

private const val Timber = "com.jakewharton.timber:timber:$TimberVersion"

fun DependencyHandler.apiTimberDependencies() {
    api(Timber)
}