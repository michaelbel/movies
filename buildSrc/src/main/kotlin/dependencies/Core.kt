package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api
import org.michaelbel.moviemade.ktx.implementation

/**
 * Core
 *
 * @see <a href="https://d.android.com/jetpack/androidx/releases">AndroidX releases</a>
 */

private const val CoreVersion = "1.8.0"
private const val CoreSplashScreenVersion = "1.0.0"

private const val Core = "androidx.core:core-ktx:$CoreVersion"
private const val CoreSplashScreen = "androidx.core:core-splashscreen:$CoreSplashScreenVersion"

fun DependencyHandler.apiCoreSplashScreenDependencies() {
    api(CoreSplashScreen)
}

fun DependencyHandler.apiCoreDependencies() {
    api(Core)
}