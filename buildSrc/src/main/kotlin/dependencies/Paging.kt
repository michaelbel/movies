package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.ktx.implementation

/**
 * Jetpack Paging
 *
 * @see <a href="https://developer.android.com/jetpack/androidx/releases/paging">Paging</a>
 */

private const val PagingComposeVersion = "1.0.0-alpha16"

private const val PagingCompose = "androidx.paging:paging-compose:$PagingComposeVersion"

fun DependencyHandler.implementationPagingDependencies() {
    implementation(PagingCompose)
}