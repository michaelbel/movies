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
private const val CoreRoleVersion = "1.0.0"
private const val CoreAnimationVersion = "1.0.0-beta01"
private const val CorePerformance = "1.0.0-alpha02"
private const val CoreGoogleShortcutsVersion = "1.0.1"
private const val CoreRemoteViewsVersion = "1.0.0-beta01"
private const val CoreSplashScreenVersion = "1.0.0"

private const val Core = "androidx.core:core-ktx:$CoreVersion"
private const val CoreRole = "androidx.core:core-role:$CoreRoleVersion"
private const val CoreAnimation = "androidx.core:core-animation:$CoreAnimationVersion"
private const val CoreAnimationTesting = "androidx.core:core-animation-testing:$CoreAnimationVersion"
private const val CorePerfomance = "androidx.core:core-performance:1.0.0-alpha02"
private const val CoreGoogleShortcuts = "androidx.core:core-google-shortcuts:$CoreGoogleShortcutsVersion"
private const val CoreRemoteViews = "androidx.core:core-remoteviews:$CoreRemoteViewsVersion"
private const val CoreSplashScreen = "androidx.core:core-splashscreen:$CoreSplashScreenVersion"

fun DependencyHandler.implementationCoreDependencies() {
    implementation(Core)
    implementation(CoreSplashScreen)
}

fun DependencyHandler.apiCoreSplashScreenDependencies() {
    api(CoreSplashScreen)
}

fun DependencyHandler.apiCoreDependencies() {
    api(Core)
    api(CoreSplashScreen)
}