@file:Suppress("SpellCheckingInspection", "unused")

package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.extensions.implementation
import org.michaelbel.moviemade.extensions.testImplementation

/**
 * Jetpack Paging
 *
 * @see <a href="https://developer.android.com/jetpack/androidx/releases/paging">Paging</a>
 */

private const val PagingVersion = "3.1.1"
private const val PagingComposeVersion = "1.0.0-alpha14"

private const val PagingRuntime = "androidx.paging:paging-runtime-ktx:$PagingVersion"
private const val PagingCompose = "androidx.paging:paging-compose:$PagingComposeVersion"
private const val PagingCommon = "androidx.paging:paging-common:$PagingVersion"

fun DependencyHandler.implementationPagingDependencies() {
    implementation(PagingRuntime)
    implementation(PagingCompose)
    testImplementation(PagingCommon)
}