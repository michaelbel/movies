package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.api

/**
 * Navigation
 *
 * @see <a href="https://developer.android.com/jetpack/androidx/releases/navigation">Navigation</a>
 */

private const val NavigationVersion = "2.5.2"

private const val NavigationCompose = "androidx.navigation:navigation-compose:$NavigationVersion"

fun DependencyHandler.apiNavigationDependencies() {
    api(NavigationCompose)
}