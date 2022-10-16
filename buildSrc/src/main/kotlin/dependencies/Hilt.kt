package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api
import org.michaelbel.moviemade.ktx.implementation
import org.michaelbel.moviemade.ktx.kapt

/**
 * Hilt
 *
 * @see <a href="https://github.com/google/dagger/releases">Hilt</a>
 */

private const val HiltVersion = "2.44"

private const val HiltAndroid = "com.google.dagger:hilt-android:$HiltVersion"
private const val HiltCompiler = "com.google.dagger:hilt-compiler:$HiltVersion"

internal fun DependencyHandler.implementationHiltDependencies() {
    implementation(HiltAndroid)
    kapt(HiltCompiler)
}