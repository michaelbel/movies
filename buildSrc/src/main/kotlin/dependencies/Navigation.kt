package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.extensions.androidTestApi
import org.michaelbel.moviemade.extensions.api

/**
 * Navigation
 *
 * @see <a href="https://developer.android.com/jetpack/androidx/releases/navigation">Navigation</a>
 */

private const val NavigationVersion = "2.5.0"

private const val NavigationFragment = "androidx.navigation:navigation-fragment-ktx:$NavigationVersion"
private const val NavigationUi = "androidx.navigation:navigation-ui-ktx:$NavigationVersion"
private const val NavigationDynamic = "androidx.navigation:navigation-dynamic-features-fragment:$NavigationVersion"
private const val NavigationTesting = "androidx.navigation:navigation-testing:$NavigationVersion"

const val NavigationSafeArgsPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$NavigationVersion"

private const val NavigationCompose = "androidx.navigation:navigation-compose:$NavigationVersion"

fun DependencyHandler.apiNavigationDependencies() {
    api(NavigationCompose)
    api(NavigationDynamic)
    api(NavigationFragment)
    api(NavigationUi)
    androidTestApi(NavigationTesting)
}